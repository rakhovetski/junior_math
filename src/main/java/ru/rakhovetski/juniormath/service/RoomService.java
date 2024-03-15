package ru.rakhovetski.juniormath.service;

import org.springframework.security.oauth2.jwt.Jwt;
import ru.rakhovetski.juniormath.domain.dto.DefaultResponseDto;
import ru.rakhovetski.juniormath.domain.dto.rooms.*;

public interface RoomService {

    RoomResponseDto createRoom(RoomCreateRequestDto requestDto, Jwt jwtToken);

    RoomResponseDto updateRoom(Integer id, RoomUpdateRequestDto requestDto, Jwt jwtToken);

    DefaultResponseDto deleteRoom(Integer id, Jwt token);

    RoomsDetailResponseDto findRoomByCode(RoomCodeRequestDto requestDto);
}
