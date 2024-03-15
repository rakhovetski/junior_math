package ru.rakhovetski.juniormath.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.IntSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rakhovetski.juniormath.domain.dto.PageResponseDto;
import ru.rakhovetski.juniormath.domain.dto.TaskFilterDto;
import ru.rakhovetski.juniormath.domain.dto.TaskCreateRequestDto;
import ru.rakhovetski.juniormath.domain.dto.TaskResponseDto;
import ru.rakhovetski.juniormath.domain.enums.ErrorCode;
import ru.rakhovetski.juniormath.entity.Subject;
import ru.rakhovetski.juniormath.entity.Task;
import ru.rakhovetski.juniormath.entity.Teacher;
import ru.rakhovetski.juniormath.exception.*;
import ru.rakhovetski.juniormath.mapper.CreatedTaskMapper;
import ru.rakhovetski.juniormath.repository.SubjectRepository;
import ru.rakhovetski.juniormath.repository.TaskRepository;
import ru.rakhovetski.juniormath.repository.TeacherRepository;
import ru.rakhovetski.juniormath.service.TaskService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final CreatedTaskMapper taskMapper;

    @Transactional(readOnly = true)
    @Override
    public PageResponseDto<TaskResponseDto> findAllTasksWithPagination(TaskFilterDto taskFilter, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        if (taskFilter.getSubjectIds() == null) {
            taskFilter.setSubjectIds(subjectRepository.findAll().stream().map(Subject::getId).collect(Collectors.toList()));
        }
        if (taskFilter.getClassNumbers() == null) {
            taskFilter.setClassNumbers(IntStream.range(1, 11).boxed().toList().stream().map(Short.class::cast).toList());
        }
        Page<Task> result = taskRepository.findAllByFilters(taskFilter.getSubjectIds(), taskFilter.getClassNumbers(), pageable);

        return PageResponseDto.of(result.map(taskMapper::map));
    }

    @Transactional(readOnly = true)
    @Override
    public TaskResponseDto findTaskById(Integer taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskDoesNotExistException(ErrorCode.TASK_DOES_NOT_EXISTS.getMessage()));

        return taskMapper.map(task);
    }

    @Override
    public TaskResponseDto createTask(TaskCreateRequestDto requestDto) {
        validateTaskRequest(requestDto);

        Teacher teacher = teacherRepository.findById(requestDto.getTeacherId()).get();
        Subject subject = subjectRepository.findById(requestDto.getSubjectId()).get();

        Task task = Task.builder()
                .classNumber(requestDto.getClassNumber())
                .topic(requestDto.getTopic())
                .condition(requestDto.getCondition())
                .answer(requestDto.getAnswer())
                .solve(requestDto.getSolve())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .teacher(teacher)
                .subject(subject)
                .build();

        taskRepository.save(task);
        return taskMapper.map(task);
    }

    private void validateTaskRequest(TaskCreateRequestDto requestDto) {
        if (subjectRepository.findById(requestDto.getSubjectId()).isEmpty()) {
            throw new SubjectNotFoundException(ErrorCode.SUBJECT_NOT_FOUND.getMessage());
        }
        if (teacherRepository.findById(requestDto.getTeacherId()).isEmpty()) {
            throw new TeacherNotFoundException(ErrorCode.TEACHER_NOT_FOUND.getMessage());
        }
    }
}
