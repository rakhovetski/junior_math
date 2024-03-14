package ru.rakhovetski.juniormath.service;

import ru.rakhovetski.juniormath.domain.dto.auth.KeycloakAccessTokenResponse;
import ru.rakhovetski.juniormath.domain.dto.auth.LoginUserRequestDto;
import ru.rakhovetski.juniormath.domain.dto.auth.RegistrationRequestDto;
import ru.rakhovetski.juniormath.domain.dto.auth.RegistrationResponseDto;

public interface AdminService {

    RegistrationResponseDto registerUser(RegistrationRequestDto requestDto);
}
