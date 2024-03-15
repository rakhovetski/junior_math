package ru.rakhovetski.juniormath.domain.dto.tests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestResponseDto {
    private Integer id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
}
