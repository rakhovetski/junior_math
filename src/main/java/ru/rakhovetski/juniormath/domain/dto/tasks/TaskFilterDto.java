package ru.rakhovetski.juniormath.domain.dto.tasks;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("subject_ids")
    private List<Integer> subjectIds;

    @JsonProperty("class_numbers")
    private List<Short> classNumbers;
}
