package ru.rakhovetski.juniormath.service.impl;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleMappingResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rakhovetski.juniormath.config.security.keycloak.KeyCloakConstants;
import ru.rakhovetski.juniormath.domain.dto.auth.RegistrationRequestDto;
import ru.rakhovetski.juniormath.domain.dto.auth.RegistrationResponseDto;
import ru.rakhovetski.juniormath.entity.Role;
import ru.rakhovetski.juniormath.entity.Student;
import ru.rakhovetski.juniormath.entity.Teacher;
import ru.rakhovetski.juniormath.entity.User;
import ru.rakhovetski.juniormath.domain.enums.ErrorCode;
import ru.rakhovetski.juniormath.exception.IncorrectTaskDataException;
import ru.rakhovetski.juniormath.exception.RegistrationInternalException;
import ru.rakhovetski.juniormath.exception.UnableCreateAdminException;
import ru.rakhovetski.juniormath.mapper.UserKeycloakMapper;
import ru.rakhovetski.juniormath.repository.StudentRepository;
import ru.rakhovetski.juniormath.repository.TeacherRepository;
import ru.rakhovetski.juniormath.repository.UserRepository;
import ru.rakhovetski.juniormath.service.AdminService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final Keycloak keycloakAdmin;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Override
    public RegistrationResponseDto registerUser(RegistrationRequestDto requestDto) {
        if (requestDto.getRole() == null) {
            requestDto.setRole(Role.STUDENT.getName());
        }

        validateRoleName(requestDto.getRole());

        RealmResource resource = keycloakAdmin.realm(KeyCloakConstants.REALM);
        UsersResource usersResource = resource.users();
        UserRepresentation user = UserKeycloakMapper.mapUserToKeycloak(requestDto);

        try (Response response = usersResource.create(user)) {
            User savedUser;
            log.info("User successfully created in keycloak with username - {}", requestDto.getUsername());
            addRealmRoleToUser(requestDto.getUsername(), requestDto.getRole());
            if (requestDto.getRole().equals(Role.STUDENT.getName())) {
                savedUser = saveUserToRepository(requestDto, Role.STUDENT);
                saveStudentToRepository(savedUser);
            } else if (requestDto.getRole().equals(Role.TEACHER.getName())) {
                savedUser = saveUserToRepository(requestDto, Role.TEACHER);
                saveTeacherToRepository(savedUser);
            } else {
                throw new UnableCreateAdminException(ErrorCode.UNABLE_CREATE_ADMIN.getMessage());
            }

            return new RegistrationResponseDto(savedUser.getId(), requestDto.getUsername(), requestDto.getEmail(), requestDto.getLastname(),
                    requestDto.getFirstname(), requestDto.getPatronymic(), requestDto.getRole());
        } catch (Exception ex) {
            log.error("An error occurred during user creation with username - {}", requestDto.getUsername());
            throw new RegistrationInternalException(ErrorCode.USER_REGISTRATION_ERROR.getMessage());
        }
    }


    private void addRealmRoleToUser(String username, String roleName) {
        RealmResource realmResource = keycloakAdmin.realm(KeyCloakConstants.REALM);
        List<UserRepresentation> users = realmResource.users().search(username);
        UserResource userResource = realmResource.users().get(users.getFirst().getId());
        RoleRepresentation role = realmResource.roles().get(roleName).toRepresentation();
        RoleMappingResource roleMappingResource = userResource.roles();
        roleMappingResource.realmLevel().add(Collections.singletonList(role));
        log.info("The role was successfully added for the user - {} | {}", username, roleName);
    }

    private void validateRoleName(String roleName) {
        log.info("Checking the role name when creating a user - {}", roleName);
        if (roleName.equals(Role.STUDENT.getName()) ||
            roleName.equals(Role.TEACHER.getName())) {
            return;
        }
        log.error("The wrong role was entered - {}", roleName);
        throw new IncorrectTaskDataException(ErrorCode.INCORRECT_ROLE_NAME.getMessage());
    }

    private User saveUserToRepository(RegistrationRequestDto requestDto, Role role) {
        User user = User.builder()
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .username(requestDto.getUsername())
                .email(requestDto.getEmail())
                .lastname(requestDto.getLastname())
                .firstname(requestDto.getFirstname())
                .patronymic(requestDto.getPatronymic())
                .role(role)
                .build();
        log.info("User successfully saved in database - {}", user.getUsername());
        return userRepository.save(user);
    }

    private void saveTeacherToRepository(User user) {
        Teacher teacher = Teacher
                .builder().user(user).build();
        teacherRepository.save(teacher);
        log.info("Teacher successfully saved - {}", user.getUsername());
    }

    private void saveStudentToRepository(User user) {
        Student student = Student
                .builder().level(1)
                .completedTasks(0).experience(0).user(user).build();
        studentRepository.save(student);
        log.info("Student successfully saved - {}", user.getUsername());
    }
}
