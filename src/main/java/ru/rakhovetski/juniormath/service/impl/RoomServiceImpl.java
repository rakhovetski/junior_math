package ru.rakhovetski.juniormath.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rakhovetski.juniormath.domain.dto.rooms.RoomCreateRequestDto;
import ru.rakhovetski.juniormath.domain.dto.rooms.RoomResponseDto;
import ru.rakhovetski.juniormath.domain.enums.ErrorCode;
import ru.rakhovetski.juniormath.entity.Room;
import ru.rakhovetski.juniormath.entity.User;
import ru.rakhovetski.juniormath.entity.UserRoom;
import ru.rakhovetski.juniormath.exception.UserNotFoundException;
import ru.rakhovetski.juniormath.repository.RoomRepository;
import ru.rakhovetski.juniormath.repository.UserRepository;
import ru.rakhovetski.juniormath.repository.UserRoomRepository;
import ru.rakhovetski.juniormath.service.RoomService;
import ru.rakhovetski.juniormath.util.RoomCodeGenerator;

import java.time.LocalDateTime;
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
    public RoomResponseDto createRoom(RoomCreateRequestDto requestDto, Jwt authenticationToken) {
        String username = authenticationToken.getClaim(USERNAME_JWT_CLAIM);
        String code = RoomCodeGenerator.generateRoomCode();
        LocalDateTime created = LocalDateTime.now();

        Room room = Room.builder().code(code).name(requestDto.getName())
                .classNumber(requestDto.getClassNumber()).createdBy(username)
                .createdAt(created).build();
        Room roomResult = roomRepository.save(room);

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage())
        );

        UserRoom userRoom = new UserRoom();
        userRoom.setRoom(room);
        userRoom.setUser(user);

        userRoomRepository.save(userRoom);

        return new RoomResponseDto(roomResult.getId(), room.getCode(), room.getName(),
                room.getClassNumber(), room.getCreatedBy(), room.getCreatedAt());
    }
}
