package ru.rakhovetski.juniormath.domain.dto.tasks;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Size(min = 1, max = 256)
    private String topic;

    @JsonProperty("condition")
    private String condition;

    @JsonProperty("answer")
    @Size(min = 1, max = 128)
    private String answer;

    @JsonProperty("solve")
    private String solve;

    @JsonProperty("subject_id")
    private Integer subjectId;
}
