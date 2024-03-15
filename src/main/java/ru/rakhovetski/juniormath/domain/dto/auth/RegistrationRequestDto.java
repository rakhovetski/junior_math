package ru.rakhovetski.juniormath.domain.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDto {

    @NotBlank
    @JsonProperty("username")
    private String username;

    @Email
    @NotBlank
    @JsonProperty("email")
    private String email;

    @NotBlank
    @JsonProperty("password")
    private String password;

    @NotBlank
    @JsonProperty("lastname")
    private String lastname;

    @NotBlank
    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("patronymic")
    private String patronymic;

    @JsonProperty("role")
    private String role;
}
