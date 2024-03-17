package ru.rakhovetski.juniormath.service;

import org.springframework.security.oauth2.jwt.Jwt;
import ru.rakhovetski.juniormath.domain.dto.DefaultResponseDto;
import ru.rakhovetski.juniormath.domain.dto.PageResponseDto;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskFilterDto;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskCreateRequestDto;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskDetailResponseDto;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskUpdateRequestDto;

public interface TaskService {

    PageResponseDto<TaskDetailResponseDto> findAllTasksWithPagination(TaskFilterDto taskFilter, Integer page, Integer size);

    TaskDetailResponseDto createTask(TaskCreateRequestDto requestDto, Jwt jwtToken);

    TaskDetailResponseDto updateTask(Integer taskId, TaskUpdateRequestDto requestDto, Jwt jwtToken);

    DefaultResponseDto deleteTask(Integer taskId, Jwt jwtToken);

    TaskDetailResponseDto findTaskById(Integer taskId);
}
