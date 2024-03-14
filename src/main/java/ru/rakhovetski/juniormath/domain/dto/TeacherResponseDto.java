package ru.rakhovetski.juniormath.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponseDto {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private String lastname;
    private String firstname;
    private String patronymic;
    private LocalDateTime localDateTime;
}
