package ru.rakhovetski.juniormath.mapper;

import org.springframework.stereotype.Component;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskResponseDto;
import ru.rakhovetski.juniormath.entity.Task;

@Component
public class CreatedTaskMapper {

    public TaskResponseDto map(Task task) {
        return new TaskResponseDto(task.getId(),
                task.getClassNumber(),
                task.getTopic(),
                task.getCondition(),
                task.getAnswer(),
                task.getSolve(),
                task.getCreatedBy(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getTeacher().getId(),
                task.getSubject().getName());
    }
}
