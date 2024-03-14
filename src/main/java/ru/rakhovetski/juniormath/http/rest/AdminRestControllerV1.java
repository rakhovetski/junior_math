package ru.rakhovetski.juniormath.http.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rakhovetski.juniormath.domain.dto.auth.KeycloakAccessTokenResponse;
import ru.rakhovetski.juniormath.domain.dto.auth.LoginUserRequestDto;
import ru.rakhovetski.juniormath.domain.dto.auth.RegistrationRequestDto;
import ru.rakhovetski.juniormath.domain.dto.auth.RegistrationResponseDto;
import ru.rakhovetski.juniormath.service.AdminService;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminRestControllerV1 {

    private final AdminService adminService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('admin')")
    public RegistrationResponseDto registerUser(@RequestBody RegistrationRequestDto requestDto) {
        return adminService.registerUser(requestDto);
    }
}
