package ru.rakhovetski.juniormath.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rakhovetski.juniormath.domain.dto.DefaultResponseDto;
import ru.rakhovetski.juniormath.domain.dto.rooms.*;
import ru.rakhovetski.juniormath.domain.dto.tests.TestResponseDto;
import ru.rakhovetski.juniormath.domain.dto.users.UserResponseDto;
import ru.rakhovetski.juniormath.domain.enums.ErrorCode;
import ru.rakhovetski.juniormath.domain.enums.SuccessCode;
import ru.rakhovetski.juniormath.entity.*;
import ru.rakhovetski.juniormath.exception.ChangeRoomException;
import ru.rakhovetski.juniormath.exception.RoomNotFoundException;
import ru.rakhovetski.juniormath.exception.UserNotFoundException;
import ru.rakhovetski.juniormath.mapper.RoomDetailsMapper;
import ru.rakhovetski.juniormath.mapper.RoomMapper;
import ru.rakhovetski.juniormath.mapper.TestMapper;
import ru.rakhovetski.juniormath.mapper.UserMapper;
import ru.rakhovetski.juniormath.repository.RoomRepository;
import ru.rakhovetski.juniormath.repository.UserRepository;
import ru.rakhovetski.juniormath.repository.UserRoomRepository;
import ru.rakhovetski.juniormath.service.RoomService;
import ru.rakhovetski.juniormath.util.RoomCodeGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private static final String USERNAME_JWT_CLAIM = "preferred_username";

    private final RoomRepository roomRepository;
    private final UserRoomRepository userRoomRepository;
    private final UserRepository userRepository;

    @Override
    public RoomResponseDto createRoom(RoomCreateRequestDto requestDto, Jwt jwtToken) {
        String username = getUsernameJwtClaim(jwtToken);
        String code = RoomCodeGenerator.generateRoomCode();

        while (roomRepository.findByCode(code).isPresent()) {
            code = RoomCodeGenerator.generateRoomCode();
        }

        LocalDateTime created = LocalDateTime.now();

        Room room = Room.builder().code(code).name(requestDto.getName())
                .classNumber(requestDto.getClassNumber()).createdBy(username)
                .createdAt(created).build();
        Room roomResult = roomRepository.save(room);

        User user = findUserByUsername(username);

        UserRoom userRoom = new UserRoom();
        userRoom.setRoom(room);
        userRoom.setUser(user);

        userRoomRepository.save(userRoom);

        return new RoomResponseDto(roomResult.getId(), room.getCode(), room.getName(),
                room.getClassNumber(), room.getCreatedBy(), room.getCreatedAt());
    }

    @Override
    public RoomResponseDto updateRoom(Integer roomId, RoomUpdateRequestDto requestDto, Jwt jwtToken) {
        Room room = findRoomById(roomId);
        String username = getUsernameJwtClaim(jwtToken);

        validateRoomCreator(username, room.getCreatedBy());

        room.setName(requestDto.getName());
        room.setClassNumber(requestDto.getClassNumber());
        Room result = roomRepository.save(room);

        return RoomMapper.map(result);
    }

    @Override
    public DefaultResponseDto deleteRoom(Integer roomId, Jwt jwtToken) {
        Room room = findRoomById(roomId);
        String username = getUsernameJwtClaim(jwtToken);

        validateRoomCreator(username, room.getCreatedBy());

        roomRepository.delete(room);

        return new DefaultResponseDto(SuccessCode.ROOM_SUCCESS_DELETED.getMessage(),
                HttpStatus.NO_CONTENT.name(),
                LocalDateTime.now());
    }

    @Override
    public RoomsDetailResponseDto findRoomByCode(RoomCodeRequestDto requestDto) {
        Room room = findRoomByCode(requestDto.getCode());
        List<Test> tests = room.getTests();
        List<User> users = new ArrayList<>();

        for(UserRoom userRoom : room.getUserRooms()) {
            users.add(userRoom.getUser());
        }

        List<TestResponseDto> testResponseDtos = TestMapper.map(tests);
        List<UserResponseDto> userResponseDtos = UserMapper.map(users);

        RoomsDetailResponseDto roomResponseDto = RoomDetailsMapper.map(room);
        roomResponseDto.setUsers(userResponseDtos);
        roomResponseDto.setTests(testResponseDtos);

        return roomResponseDto;
    }

    private Room findRoomByCode(String code) {
        return roomRepository.findByCode(code).orElseThrow(
                () -> new RoomNotFoundException(ErrorCode.ROOM_NOT_FOUND.getMessage())
        );
    }

    private Room findRoomById(Integer id) {
        return roomRepository.findById(id).orElseThrow(
                () -> new RoomNotFoundException(ErrorCode.ROOM_NOT_FOUND.getMessage())
        );
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage())
        );
    }

    private String getUsernameJwtClaim(Jwt jwtToken) {
        return jwtToken.getClaim(USERNAME_JWT_CLAIM);
    }

    private void validateRoomCreator(String requestUsername, String roomCreatedBy) {
        if (!requestUsername.equals(roomCreatedBy)) {
            throw new ChangeRoomException(ErrorCode.TEACHER_CANNOT_CHANGE_ROOM.getMessage());
        }
    }
}
