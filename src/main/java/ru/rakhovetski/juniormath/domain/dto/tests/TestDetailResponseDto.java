package ru.rakhovetski.juniormath.domain.dto.tests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskResponseDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDetailResponseDto {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("started_at")
    private LocalDateTime startedAt;

    @JsonProperty("tasks")
    private List<TaskResponseDto> tasks;
}
