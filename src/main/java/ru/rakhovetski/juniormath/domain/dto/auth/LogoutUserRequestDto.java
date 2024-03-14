package ru.rakhovetski.juniormath.domain.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogoutUserRequestDto {

    private String refreshToken;
}
