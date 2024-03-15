package ru.rakhovetski.juniormath.domain.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogoutUserRequestDto {

    @JsonProperty("refresh_token")
    private String refreshToken;
}
