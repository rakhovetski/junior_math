package ru.rakhovetski.juniormath.integrational.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.jdbc.Sql;
import ru.rakhovetski.juniormath.domain.dto.DefaultResponseDto;
import ru.rakhovetski.juniormath.domain.dto.PageResponseDto;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskCreateRequestDto;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskFilterDto;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskDetailResponseDto;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskUpdateRequestDto;
import ru.rakhovetski.juniormath.exception.ChangeTaskException;
import ru.rakhovetski.juniormath.exception.SubjectNotFoundException;
import ru.rakhovetski.juniormath.exception.TaskNotFoundException;
import ru.rakhovetski.juniormath.exception.TeacherNotFoundException;
import ru.rakhovetski.juniormath.integrational.IntegrationBaseTest;
import ru.rakhovetski.juniormath.integrational.util.JwtGenerator;
import ru.rakhovetski.juniormath.integrational.util.TaskDataGenerator;
import ru.rakhovetski.juniormath.service.TaskService;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql({
        "/db/data.sql"
})
public class TaskServiceTest extends IntegrationBaseTest {

    @Autowired
    private TaskService taskService;

    @Test
    public void testFindAllTasksWithPaginationWithNoTaskFilterDto() {
        Integer page = 0;
        Integer size = 10;

        String firstTaskTopic = "topicAl";
        String firstTaskCondition = "conditionAl";
        String firstTaskAnswer = "answerAl";
        String firstTaskSolve = "solveAl";
        String firstTaskCreatedBy = "username";

        TaskFilterDto taskFilterDto = null;

        PageResponseDto<TaskDetailResponseDto> result = taskService.findAllTasksWithPagination(taskFilterDto, page, size);

        TaskDetailResponseDto firstTask = result.getData().getFirst();

        assertEquals(firstTaskTopic, firstTask.getTopic());
        assertEquals(firstTaskCondition, firstTask.getCondition());
        assertEquals(firstTaskAnswer, firstTask.getAnswer());
        assertEquals(firstTaskSolve, firstTask.getSolve());
        assertEquals(firstTaskCreatedBy, firstTask.getCreatedBy());

        assertEquals(3, result.getMetadata().getTotalElements());
        assertEquals(0, result.getMetadata().getPage());
        assertEquals(10, result.getMetadata().getSize());
    }

    @Test
    public void testFindAllTasksWithPaginationWithTaskFilterDto() {
        Integer page = 0;
        Integer size = 10;

        List<Integer> subjectIds = List.of(1);
        List<Short> classNumbers = List.of((short) 9);

        String firstTaskTopic = "topicAl";
        String firstTaskCondition = "conditionAl";
        String firstTaskAnswer = "answerAl";
        String firstTaskSolve = "solveAl";
        String firstTaskCreatedBy = "username";

        TaskFilterDto taskFilterDto = new TaskFilterDto(subjectIds, classNumbers);

        PageResponseDto<TaskDetailResponseDto> result = taskService.findAllTasksWithPagination(taskFilterDto, page, size);

        TaskDetailResponseDto firstTask = result.getData().getFirst();

        assertEquals(firstTaskTopic, firstTask.getTopic());
        assertEquals(firstTaskCondition, firstTask.getCondition());
        assertEquals(firstTaskAnswer, firstTask.getAnswer());
        assertEquals(firstTaskSolve, firstTask.getSolve());
        assertEquals(firstTaskCreatedBy, firstTask.getCreatedBy());

        assertEquals(1, result.getMetadata().getTotalElements());
        assertEquals(0, result.getMetadata().getPage());
        assertEquals(10, result.getMetadata().getSize());
    }

    @Test
    public void testFindTaskByIdCorrectId() {
        String createdBy = "username";
        Jwt jwt = JwtGenerator.generateJwt(createdBy);

        TaskCreateRequestDto createRequestDto = TaskDataGenerator.generateCreateTask();
        var result = taskService.createTask(createRequestDto, jwt);

        TaskDetailResponseDto response = taskService.findTaskById(result.getId());

        assertEquals(createRequestDto.getTopic(), response.getTopic());
        assertEquals(createRequestDto.getCondition(), response.getCondition());
        assertEquals(createRequestDto.getAnswer(), response.getAnswer());
        assertEquals(createRequestDto.getSolve(), response.getSolve());
        assertEquals(createdBy, response.getCreatedBy());
    }

    @Test
    public void testFindTaskByIdIncorrectId() {
        Integer taskId = 1_000_000;

        assertThrows(TaskNotFoundException.class, () -> taskService.findTaskById(taskId));
    }

    @Test
    public void testCreateTaskCorrectData() {
        String createdBy = "username";
        Jwt jwt = JwtGenerator.generateJwt(createdBy);

        TaskCreateRequestDto createRequestDto = TaskDataGenerator.generateCreateTask();

        String firstTaskTopic = createRequestDto.getTopic();
        String firstTaskCondition = createRequestDto.getCondition();
        String firstTaskAnswer = createRequestDto.getAnswer();
        String firstTaskSolve = createRequestDto.getSolve();

        var result = taskService.createTask(createRequestDto, jwt);

        assertEquals(firstTaskTopic, result.getTopic());
        assertEquals(firstTaskCondition, result.getCondition());
        assertEquals(firstTaskAnswer, result.getAnswer());
        assertEquals(firstTaskSolve, result.getSolve());
        assertEquals(createdBy, result.getCreatedBy());
    }

    @Test
    public void testCreateTaskIncorrectTeacherUsername() {
        String createdBy = "username-incorrect";
        Jwt jwt = JwtGenerator.generateJwt(createdBy);

        TaskCreateRequestDto createRequestDto = TaskDataGenerator.generateCreateTask();

        assertThrows(TeacherNotFoundException.class, () -> taskService.createTask(createRequestDto, jwt));
    }

    @Test
    public void testCreateTaskIncorrectSubjectId() {
        Integer incorrectSubjectId = 1_000;
        String createdBy = "username-incorrect";
        Jwt jwt = JwtGenerator.generateJwt(createdBy);

        TaskCreateRequestDto createRequestDto = TaskDataGenerator.generateCreateTask();

        createRequestDto.setSubjectId(incorrectSubjectId);

        assertThrows(SubjectNotFoundException.class, () -> taskService.createTask(createRequestDto, jwt));
    }

    @Test
    public void testUpdateTaskCorrect() {
        String createdBy = "username";
        Jwt jwt = JwtGenerator.generateJwt(createdBy);

        TaskCreateRequestDto createRequestDto = TaskDataGenerator.generateCreateTask();

        var result = taskService.createTask(createRequestDto, jwt);

        TaskUpdateRequestDto updateRequestDto = TaskDataGenerator.generateUpdateTask();

        String firstTaskTopic = updateRequestDto.getTopic();
        String firstTaskCondition = updateRequestDto.getCondition();
        String firstTaskAnswer = updateRequestDto.getAnswer();
        String firstTaskSolve = updateRequestDto.getSolve();

        var resultUpdate = taskService.updateTask(result.getId(), updateRequestDto, jwt);

        assertEquals(firstTaskTopic, resultUpdate.getTopic());
        assertEquals(firstTaskCondition, resultUpdate.getCondition());
        assertEquals(firstTaskAnswer, resultUpdate.getAnswer());
        assertEquals(firstTaskSolve, resultUpdate.getSolve());
        assertEquals(createdBy, resultUpdate.getCreatedBy());
    }

    @Test
    public void testUpdateTaskIncorrectUsername() {
        String createdBy = "username";
        Jwt jwt = JwtGenerator.generateJwt(createdBy);

        TaskCreateRequestDto createRequestDto = TaskDataGenerator.generateCreateTask();

        var result = taskService.createTask(createRequestDto, jwt);

        String updateBy = "usernameincorrect";
        Jwt jwt1 = JwtGenerator.generateJwt(updateBy);

        TaskUpdateRequestDto updateRequestDto = TaskDataGenerator.generateUpdateTask();

        assertThrows(ChangeTaskException.class, () -> taskService.updateTask(result.getId(), updateRequestDto, jwt1));
    }

    @Test
    public void testUpdateTaskIncorrectSubjectId() {
        String createdBy = "username";
        Integer incorrectId = 1_000;
        Jwt jwt = JwtGenerator.generateJwt(createdBy);

        TaskCreateRequestDto createRequestDto = TaskDataGenerator.generateCreateTask();

        var result = taskService.createTask(createRequestDto, jwt);

        TaskUpdateRequestDto updateRequestDto = TaskDataGenerator.generateUpdateTask();
        updateRequestDto.setSubjectId(incorrectId);

        assertThrows(SubjectNotFoundException.class, () -> taskService.updateTask(result.getId(), updateRequestDto, jwt));
    }

    @Test
    public void testDeleteTaskCorrectData() {
        String createdBy = "username";
        Jwt jwt = JwtGenerator.generateJwt(createdBy);

        TaskCreateRequestDto createRequestDto = TaskDataGenerator.generateCreateTask();

        var result = taskService.createTask(createRequestDto, jwt);

        DefaultResponseDto response = taskService.deleteTask(result.getId(), jwt);

        assertEquals(HttpStatus.NO_CONTENT.name(), response.getStatus());
    }

    @Test
    public void testDeleteTaskIncorrectUsername() {
        String createdBy = "username";
        Jwt jwt = JwtGenerator.generateJwt(createdBy);

        TaskCreateRequestDto createRequestDto = TaskDataGenerator.generateCreateTask();

        String deleteBy = "username-incorrect";
        Jwt jwt1 = JwtGenerator.generateJwt(deleteBy);

        var result = taskService.createTask(createRequestDto, jwt);

        assertThrows(ChangeTaskException.class, () -> taskService.deleteTask(result.getId(), jwt1));
    }
}
