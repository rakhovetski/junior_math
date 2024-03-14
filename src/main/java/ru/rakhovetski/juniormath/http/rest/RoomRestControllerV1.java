package ru.rakhovetski.juniormath.http.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rakhovetski.juniormath.domain.dto.rooms.RoomCreateRequestDto;
import ru.rakhovetski.juniormath.domain.dto.rooms.RoomResponseDto;
import ru.rakhovetski.juniormath.service.RoomService;

@Slf4j
@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomRestControllerV1 {

    private final RoomService roomService;

    @PostMapping()
    @PreAuthorize("hasRole('admin') || hasRole('teacher')")
    public RoomResponseDto createRoom(@RequestBody RoomCreateRequestDto requestDto,
                                      @AuthenticationPrincipal Jwt token) {
        return roomService.createRoom(requestDto, token);
    }


}
