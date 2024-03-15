package ru.rakhovetski.juniormath.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskFilterDto {

    private List<Integer> subjectIds;
    private List<Short> classNumbers;
}
