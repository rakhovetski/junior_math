package ru.rakhovetski.juniormath.integrational.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.rakhovetski.juniormath.domain.dto.PageResponseDto;
import ru.rakhovetski.juniormath.domain.dto.TaskFilterDto;
import ru.rakhovetski.juniormath.domain.dto.TaskResponseDto;
import ru.rakhovetski.juniormath.integrational.IntegrationBaseTest;
import ru.rakhovetski.juniormath.service.TaskService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql({
        "/db/data.sql"
})
@RequiredArgsConstructor
public class TaskServiceTest extends IntegrationBaseTest {

    @Autowired
    private TaskService taskService;

    @Test
    public void testCorrectFindAllTasksWithPaginationNoFilter() {
        TaskFilterDto taskFilterDto = new TaskFilterDto();
        int pageNumber = 0;
        int pageSize = 10;

        PageResponseDto<TaskResponseDto> result = taskService.findAllTasksWithPagination(taskFilterDto, pageNumber, pageSize);

        assertEquals(3L, result.getMetadata().getTotalElements());

    }

}
