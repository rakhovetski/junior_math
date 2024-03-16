package ru.rakhovetski.juniormath.domain.dto.tasks;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskUpdateRequestDto {

    @Max(11)
    @Min(1)
    @JsonProperty("class_number")
    private Short classNumber;

    @JsonProperty("topic")
    private String topic;

    @JsonProperty("condition")
    private String condition;

    @JsonProperty("answer")
    private String answer;

    @JsonProperty("solve")
    private String solve;
}
