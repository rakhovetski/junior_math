package ru.rakhovetski.juniormath.service;

import org.springframework.http.ResponseEntity;
import ru.rakhovetski.juniormath.domain.dto.auth.KeycloakAccessTokenResponse;
import ru.rakhovetski.juniormath.domain.dto.auth.LoginUserRequestDto;
import ru.rakhovetski.juniormath.domain.dto.auth.LogoutUserRequestDto;

public interface AuthenticationService {

    KeycloakAccessTokenResponse loginUser(LoginUserRequestDto loginUserDto);

    ResponseEntity<?> logoutUser(LogoutUserRequestDto requestDto);
}
