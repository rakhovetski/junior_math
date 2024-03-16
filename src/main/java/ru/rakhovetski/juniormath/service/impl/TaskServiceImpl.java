package ru.rakhovetski.juniormath.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rakhovetski.juniormath.domain.dto.DefaultResponseDto;
import ru.rakhovetski.juniormath.domain.dto.PageResponseDto;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskFilterDto;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskCreateRequestDto;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskResponseDto;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskUpdateRequestDto;
import ru.rakhovetski.juniormath.domain.enums.ErrorCode;
import ru.rakhovetski.juniormath.domain.enums.SuccessCode;
import ru.rakhovetski.juniormath.entity.Subject;
import ru.rakhovetski.juniormath.entity.Task;
import ru.rakhovetski.juniormath.entity.Teacher;
import ru.rakhovetski.juniormath.exception.*;
import ru.rakhovetski.juniormath.mapper.CreatedTaskMapper;
import ru.rakhovetski.juniormath.repository.SubjectRepository;
import ru.rakhovetski.juniormath.repository.TaskRepository;
import ru.rakhovetski.juniormath.repository.TeacherRepository;
import ru.rakhovetski.juniormath.service.TaskService;
import ru.rakhovetski.juniormath.util.DataByJwtUtil;

import java.time.LocalDateTime;
import java.util.Optional;
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
            log.info("Passed subject ids == null, all subject ids are used");
            taskFilter.setSubjectIds(subjectRepository.findAll().stream().map(Subject::getId).collect(Collectors.toList()));
        }
        if (taskFilter.getClassNumbers() == null) {
            log.info("Passed class numbers == null, all class numbers are used");
            taskFilter.setClassNumbers(IntStream.range(1, 11).boxed().map(Integer::shortValue).collect(Collectors.toList()));
        }
        Page<Task> result = taskRepository.findAllByFilters(taskFilter.getSubjectIds(), taskFilter.getClassNumbers(), pageable);

        return PageResponseDto.of(result.map(taskMapper::map));
    }

    @Transactional(readOnly = true)
    @Override
    public TaskResponseDto findTaskById(Integer taskId) {
        Task task = findTaskByIdCheck(taskId);

        log.info("Task find by id - {}, with topic {}", taskId, task.getTopic());

        return taskMapper.map(task);
    }

    @Override
    public TaskResponseDto createTask(TaskCreateRequestDto requestDto, Jwt jwtToken) {
        validateTaskRequest(requestDto);

        Teacher teacher = teacherRepository.findById(requestDto.getTeacherId()).get();
        Subject subject = subjectRepository.findById(requestDto.getSubjectId()).get();

        String username = DataByJwtUtil.getUsernameJwtClaim(jwtToken);

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
                .createdBy(username)
                .build();

        Task result = taskRepository.save(task);

        log.info("The task was successfully created with the topic - {}", requestDto.getTopic());

        return taskMapper.map(result);
    }

    @Override
    public TaskResponseDto updateTask(Integer taskId, TaskUpdateRequestDto requestDto, Jwt jwtToken) {
        Task task = findTaskByIdCheck(taskId);
        String requestUsername = DataByJwtUtil.getUsernameJwtClaim(jwtToken);

        validateTaskCreator(requestUsername, task.getCreatedBy());

        task.setClassNumber(Optional.ofNullable(requestDto.getClassNumber()).orElse(task.getClassNumber()));
        task.setAnswer(Optional.ofNullable(requestDto.getAnswer()).orElse(task.getAnswer()));
        task.setTopic(Optional.ofNullable(requestDto.getTopic()).orElse(task.getTopic()));
        task.setSolve(Optional.ofNullable(requestDto.getSolve()).orElse(task.getSolve()));
        task.setCondition(Optional.ofNullable(requestDto.getCondition()).orElse(task.getCondition()));

        Task result = taskRepository.save(task);

        log.info("The task was successfully updated with the topic - {}", result.getTopic());

        return taskMapper.map(result);
    }

    @Override
    public DefaultResponseDto deleteTask(Integer taskId, Jwt jwtToken) {
        Task task = findTaskByIdCheck(taskId);
        String requestUsername = DataByJwtUtil.getUsernameJwtClaim(jwtToken);

        validateTaskCreator(requestUsername, task.getCreatedBy());

        taskRepository.delete(task);

        log.info("The task was successfully deleted with the topic - {}", task.getTopic());

        return new DefaultResponseDto(SuccessCode.TASK_SUCCESS_DELETED.getMessage(),
                HttpStatus.NO_CONTENT.name(),
                LocalDateTime.now());
    }

    private void validateTaskRequest(TaskCreateRequestDto requestDto) {
        if (subjectRepository.findById(requestDto.getSubjectId()).isEmpty()) {
            log.error("Error - the subject was not found by id");
            throw new SubjectNotFoundException(ErrorCode.SUBJECT_NOT_FOUND.getMessage());
        }
        if (teacherRepository.findById(requestDto.getTeacherId()).isEmpty()) {
            log.error("Error - the teacher was not found by id");
            throw new TeacherNotFoundException(ErrorCode.TEACHER_NOT_FOUND.getMessage());
        }
    }

    private Task findTaskByIdCheck(Integer id) {
        return taskRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Error - the task was not found by id");
                    return new TaskNotFoundException(ErrorCode.TASK_NOT_FOUND.getMessage());
                }
        );
    }

    private void validateTaskCreator(String requestUsername, String taskCreatedBy) {
        if (!requestUsername.equals(taskCreatedBy)) {
            log.error("Error - the creator of the task does not match the current teacher");
            throw new ChangeTaskException(ErrorCode.TEACHER_CANNOT_CHANGE_TASK.getMessage());
        }
    }
}
