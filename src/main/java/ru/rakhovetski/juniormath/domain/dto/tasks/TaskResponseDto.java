package ru.rakhovetski.juniormath.domain.dto.tasks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDto {
    private Integer id;
    private Short classNumber;
    private String topic;
    private String condition;
    private String answer;
    private String solve;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer teacherId;
    private String subjectName;
}
