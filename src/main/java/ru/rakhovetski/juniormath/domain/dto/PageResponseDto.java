package ru.rakhovetski.juniormath.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDto <T> {
    private List<T> data;
    private Metadata metadata;

    public static <T> PageResponseDto<T> of(Page<T> page) {
        Metadata metadata1 = new Metadata(page.getSize(), page.getNumber(), page.getTotalElements());
        return new PageResponseDto<>(page.getContent(), metadata1);
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Metadata {
        private Integer size;
        private Integer page;
        private Long totalElements;
    }
}
