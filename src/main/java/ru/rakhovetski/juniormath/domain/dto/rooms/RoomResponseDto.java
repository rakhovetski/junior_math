package ru.rakhovetski.juniormath.domain.dto.rooms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponseDto {
    private Integer id;
    private String code;
    private String name;
    private Short classNumber;
    private String createdBy;
    private LocalDateTime createdAt;
}
