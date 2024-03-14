package ru.rakhovetski.juniormath.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dto request create task")
public class TaskCreateRequestDto {

    @Max(11)
    @Min(1)
    @JsonProperty("class_number")
    private Short classNumber;

    @NotBlank
    @JsonProperty("topic")
    private String topic;

    @NotBlank
    @JsonProperty("condition")
    private String condition;

    @NotBlank
    @JsonProperty("answer")
    private String answer;

    @NotBlank
    @JsonProperty("solve")
    private String solve;

    @NotBlank
    @JsonProperty("subject_id")
    private Integer subjectId;

    @NotBlank
    @JsonProperty("teacher_id")
    private Integer teacherId;
}
