package ru.rakhovetski.juniormath.mapper;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import ru.rakhovetski.juniormath.domain.dto.auth.RegistrationRequestDto;

import java.util.Collections;

public class UserKeycloakMapper {

    public static UserRepresentation mapUserToKeycloak(RegistrationRequestDto requestDto) {
        UserRepresentation userRepresentation = new UserRepresentation();

        userRepresentation.setEmail(requestDto.getEmail());
        userRepresentation.setUsername(requestDto.getUsername());
        userRepresentation.setLastName(requestDto.getLastname());
        userRepresentation.setFirstName(requestDto.getFirstname());
        userRepresentation.singleAttribute("patronymic", requestDto.getPatronymic());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(requestDto.getPassword());
        credential.setTemporary(false);

        userRepresentation.setCredentials(Collections.singletonList(credential));
        return userRepresentation;
    }
}
