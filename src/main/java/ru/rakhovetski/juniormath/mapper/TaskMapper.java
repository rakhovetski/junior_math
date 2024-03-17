package ru.rakhovetski.juniormath.mapper;

import ru.rakhovetski.juniormath.domain.dto.tasks.TaskResponseDto;
import ru.rakhovetski.juniormath.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskMapper {

    public static TaskResponseDto map(Task task) {
        return new TaskResponseDto(task.getId(),
                task.getClassNumber(),
                task.getTopic(),
                task.getCondition(),
                task.getCreatedBy(),
                task.getCreatedAt());
    }

    public static List<TaskResponseDto> map(List<Task> tasks) {
        return new ArrayList<>(tasks.stream().map(TaskMapper::map).toList());
    }
}
