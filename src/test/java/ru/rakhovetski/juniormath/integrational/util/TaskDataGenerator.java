package ru.rakhovetski.juniormath.integrational.util;

import org.jetbrains.annotations.NotNull;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskCreateRequestDto;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskUpdateRequestDto;

public class TaskDataGenerator {

    public static TaskCreateRequestDto generateCreateTask() {
        return new TaskCreateRequestDto((short) 9, "topicGen", "conditionGen", "answerGen", "solveGen", 1);
    }

    public static TaskUpdateRequestDto generateUpdateTask() {
        TaskUpdateRequestDto updateRequestDto = new TaskUpdateRequestDto();

        updateRequestDto.setAnswer("new-answer");
        updateRequestDto.setCondition("new-condition");
        updateRequestDto.setTopic("new-topic");
        updateRequestDto.setSolve("new-solve");

        return updateRequestDto;
    }
}
