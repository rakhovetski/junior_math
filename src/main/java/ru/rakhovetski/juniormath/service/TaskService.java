package ru.rakhovetski.juniormath.service;

import org.springframework.security.oauth2.jwt.Jwt;
import ru.rakhovetski.juniormath.domain.dto.DefaultResponseDto;
import ru.rakhovetski.juniormath.domain.dto.PageResponseDto;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskFilterDto;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskCreateRequestDto;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskResponseDto;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskUpdateRequestDto;

public interface TaskService {

    PageResponseDto<TaskResponseDto> findAllTasksWithPagination(TaskFilterDto taskFilter, Integer page, Integer size);

    TaskResponseDto createTask(TaskCreateRequestDto requestDto, Jwt jwtToken);

    TaskResponseDto updateTask(Integer taskId, TaskUpdateRequestDto requestDto, Jwt jwtToken);

    DefaultResponseDto deleteTask(Integer taskId, Jwt jwtToken);

    TaskResponseDto findTaskById(Integer taskId);
}
