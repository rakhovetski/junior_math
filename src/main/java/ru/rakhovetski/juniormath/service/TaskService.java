package ru.rakhovetski.juniormath.service;

import ru.rakhovetski.juniormath.domain.dto.PageResponseDto;
import ru.rakhovetski.juniormath.domain.dto.TaskFilterDto;
import ru.rakhovetski.juniormath.domain.dto.TaskCreateRequestDto;
import ru.rakhovetski.juniormath.domain.dto.TaskResponseDto;

public interface TaskService {

    PageResponseDto<TaskResponseDto> findAllTasksWithPagination(TaskFilterDto taskFilter, Integer page, Integer size);

    TaskResponseDto createTask(TaskCreateRequestDto requestDto);

    TaskResponseDto findTaskById(Integer taskId);
}
