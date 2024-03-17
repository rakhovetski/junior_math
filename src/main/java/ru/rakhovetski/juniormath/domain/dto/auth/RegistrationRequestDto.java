package ru.rakhovetski.juniormath.domain.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDto {

    @NotBlank
    @Size(min = 2, max = 128)
    @JsonProperty("username")
    private String username;

    @Email
    @Size(min = 4, max = 128)
    @NotBlank
    @JsonProperty("email")
    private String email;

    @NotBlank
    @Size(min = 8, max = 128)
    @JsonProperty("password")
    private String password;

    @NotBlank
    @Size(min = 2, max = 128)
    @JsonProperty("lastname")
    private String lastname;

    @NotBlank
    @Size(min = 2, max = 128)
    @JsonProperty("firstname")
    private String firstname;

    @Size(min = 2, max = 128)
    @JsonProperty("patronymic")
    private String patronymic;

    @JsonProperty("role")
    private String role;
}
