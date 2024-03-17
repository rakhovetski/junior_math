package ru.rakhovetski.juniormath.domain.dto.tests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestUpdateRequestDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("started_at")
    private LocalDateTime startedAt;
}
