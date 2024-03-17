package ru.rakhovetski.juniormath.domain.dto.tests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCreateRequestDto {

    @NotBlank
    @Size(min = 2, max = 256)
    @JsonProperty("name")
    private String name;

    @NotBlank
    @JsonProperty("started_at")
    private LocalDateTime startedAt;
}
