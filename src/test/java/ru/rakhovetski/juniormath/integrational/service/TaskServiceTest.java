package ru.rakhovetski.juniormath.integrational.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
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
}
