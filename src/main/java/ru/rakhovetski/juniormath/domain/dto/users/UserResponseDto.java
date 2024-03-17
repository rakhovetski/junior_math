package ru.rakhovetski.juniormath.domain.dto.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rakhovetski.juniormath.entity.Role;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("patronymic")
    private String patronymic;

    @JsonProperty("role")
    private Role role;
}
