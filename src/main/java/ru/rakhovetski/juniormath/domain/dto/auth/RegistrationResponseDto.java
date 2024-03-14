package ru.rakhovetski.juniormath.domain.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponseDto {

    private String username;
    private String email;
    private String lastname;
    private String firstname;
    private String patronymic;
    private String role;
}
