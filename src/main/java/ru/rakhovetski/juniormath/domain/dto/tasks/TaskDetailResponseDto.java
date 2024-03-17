package ru.rakhovetski.juniormath.domain.dto.tasks;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDetailResponseDto {

    @JsonProperty("id")
    private Integer id;

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

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @JsonProperty("teacher_id")
    private Integer teacherId;

    @JsonProperty("subject_name")
    private String subjectName;
}
