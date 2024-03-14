package ru.rakhovetski.juniormath.http.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rakhovetski.juniormath.domain.dto.auth.KeycloakAccessTokenResponse;
import ru.rakhovetski.juniormath.domain.dto.auth.LoginUserRequestDto;
import ru.rakhovetski.juniormath.domain.dto.auth.LogoutUserRequestDto;
import ru.rakhovetski.juniormath.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationRestControllerV1 {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public KeycloakAccessTokenResponse loginUser(@RequestBody LoginUserRequestDto requestDto) {
        return authenticationService.loginUser(requestDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestBody LogoutUserRequestDto logoutUserRequestDto) {
        return authenticationService.logoutUser(logoutUserRequestDto);
    }
}
