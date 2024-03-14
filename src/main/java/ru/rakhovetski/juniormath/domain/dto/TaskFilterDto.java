package ru.rakhovetski.juniormath.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskFilterDto {

    private Integer subjectId;
    private Short classNumber;
}
