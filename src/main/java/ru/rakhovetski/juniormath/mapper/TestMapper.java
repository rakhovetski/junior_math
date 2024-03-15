package ru.rakhovetski.juniormath.mapper;

import ru.rakhovetski.juniormath.domain.dto.tests.TestResponseDto;
import ru.rakhovetski.juniormath.entity.Test;

import java.util.ArrayList;
import java.util.List;

public class TestMapper {

    public static List<TestResponseDto> map(List<Test> tests) {
        return new ArrayList<>(tests.stream().map(TestMapper::map).toList());
    }

    public static TestResponseDto map(Test test) {
        return new TestResponseDto(
                test.getId(),
                test.getName(),
                test.getCreatedAt(),
                test.getStartedAt());
    }
}
