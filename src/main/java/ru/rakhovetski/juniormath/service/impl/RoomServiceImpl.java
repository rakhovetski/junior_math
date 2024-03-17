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
import ru.rakhovetski.juniormath.util.DataByJwtUtil;
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

    private final RoomRepository roomRepository;
    private final UserRoomRepository userRoomRepository;
    private final UserRepository userRepository;

    @Override
    public RoomResponseDto createRoom(RoomCreateRequestDto requestDto, Jwt jwtToken) {
        String username = DataByJwtUtil.getUsernameJwtClaim(jwtToken);
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

        log.info("The room was successfully created with the name - {}", requestDto.getName());

        return new RoomResponseDto(roomResult.getId(), room.getCode(), room.getName(),
                room.getClassNumber(), room.getCreatedBy(), room.getCreatedAt());
    }

    @Override
    public RoomResponseDto updateRoom(String code, RoomUpdateRequestDto requestDto, Jwt jwtToken) {
        Room room = findRoomByCode(code);
        String username = DataByJwtUtil.getUsernameJwtClaim(jwtToken);

        validateRoomCreator(username, room.getCreatedBy());

        room.setName(Optional.ofNullable(requestDto.getName()).orElse(room.getName()));
        room.setClassNumber(Optional.ofNullable(requestDto.getClassNumber()).orElse(room.getClassNumber()));
        Room result = roomRepository.save(room);

        log.info("The room was successfully updated with the name - {}", requestDto.getName());

        return RoomMapper.map(result);
    }

    @Override
    public DefaultResponseDto deleteRoom(String code, Jwt jwtToken) {
        Room room = findRoomByCode(code);
        String username = DataByJwtUtil.getUsernameJwtClaim(jwtToken);

        validateRoomCreator(username, room.getCreatedBy());

        userRoomRepository.deleteAll(room.getUserRooms());

        roomRepository.delete(room);

        log.info("The room was successfully deleted with the name - {}", room.getName());

        return new DefaultResponseDto(SuccessCode.ROOM_SUCCESS_DELETED.getMessage(),
                HttpStatus.NO_CONTENT.name(),
                LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    @Override
    public RoomsDetailResponseDto findRoomByCode(RoomCodeRequestDto requestDto) {
        Room room = findRoomByCode(requestDto.getCode());
        List<Test> tests = room.getTests();
        List<User> users = new ArrayList<>();

        for (UserRoom userRoom : room.getUserRooms()) {
            users.add(userRoom.getUser());
        }

        List<TestResponseDto> testResponseDtos = TestMapper.map(tests);
        List<UserResponseDto> userResponseDtos = UserMapper.map(users);

        RoomsDetailResponseDto roomResponseDto = RoomDetailsMapper.map(room);
        roomResponseDto.setUsers(userResponseDtos);
        roomResponseDto.setTests(testResponseDtos);

        log.info("Room find by code - {}, with name {}", requestDto.getCode(), room.getName());

        return roomResponseDto;
    }

    private Room findRoomByCode(String code) {
        return roomRepository.findByCode(code).orElseThrow(
                () -> {
                    log.error("Error - the room was not found by code");
                    return new RoomNotFoundException(ErrorCode.ROOM_NOT_FOUND.getMessage());
                }
        );
    }


    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> {
                    log.error("Error - the user was not found by username");
                    return new UserNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage());
                }
        );
    }

    private void validateRoomCreator(String requestUsername, String roomCreatedBy) {
        if (!requestUsername.equals(roomCreatedBy)) {
            log.error("Error - the creator of the room does not match the current teacher");
            throw new ChangeRoomException(ErrorCode.TEACHER_CANNOT_CHANGE_ROOM.getMessage());
        }
    }
}
