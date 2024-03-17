package ru.rakhovetski.juniormath.mapper;

import ru.rakhovetski.juniormath.domain.dto.tasks.TaskResponseDto;
import ru.rakhovetski.juniormath.domain.dto.tests.TestDetailResponseDto;
import ru.rakhovetski.juniormath.entity.Test;

import java.util.List;

public class TestDetailMapper {

    public static TestDetailResponseDto map(Test test, List<TaskResponseDto> responseDtos) {
        return new TestDetailResponseDto(test.getId(),
                test.getName(),
                test.getCreatedAt(),
                test.getStartedAt(),
                responseDtos);
    }
}
