package ru.rakhovetski.juniormath.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final SubjectRepository subjectNameRepository;
    private final TeacherRepository teacherRepository;
    private final CreatedTaskMapper taskMapper;

    @Transactional(readOnly = true)
    @Override
    public PageResponseDto<TaskResponseDto> findAllTasksWithPagination(TaskFilterDto taskFilter, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> result;
        if (taskFilter.getClassNumber() != null && taskFilter.getSubjectId() != null) {
            result = taskRepository.findAllBySubjectIdAndClassNumber(taskFilter.getSubjectId(), taskFilter.getClassNumber(), pageable);
        } else if (taskFilter.getSubjectId() != null) {
            result = taskRepository.findAllBySubjectId(taskFilter.getSubjectId(), pageable);
        } else if (taskFilter.getClassNumber() != null) {
            result = taskRepository.findAllByClassNumber(taskFilter.getClassNumber(), pageable);
        } else {
            result = taskRepository.findAllBy(pageable);
        }

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
        Subject subject = subjectNameRepository.findById(requestDto.getSubjectId()).get();

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
        if (requestDto.getTeacherId() == null || requestDto.getSubjectId() == null) {
            throw new IncorrectTaskDataException(ErrorCode.INCORRECT_TASK_DATA.getMessage());
        }
        if (subjectNameRepository.findById(requestDto.getSubjectId()).isEmpty()) {
            throw new SubjectNotFoundException(ErrorCode.SUBJECT_NOT_FOUND.getMessage());
        }
        if (teacherRepository.findById(requestDto.getTeacherId()).isEmpty()) {
            throw new TeacherNotFoundException(ErrorCode.TEACHER_NOT_FOUND.getMessage());
        }
    }
}
