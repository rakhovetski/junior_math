package ru.rakhovetski.juniormath.service;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import ru.rakhovetski.juniormath.domain.dto.rooms.RoomCreateRequestDto;
import ru.rakhovetski.juniormath.domain.dto.rooms.RoomResponseDto;

public interface RoomService {

    RoomResponseDto createRoom(RoomCreateRequestDto requestDto, Jwt authenticationToken);


}
