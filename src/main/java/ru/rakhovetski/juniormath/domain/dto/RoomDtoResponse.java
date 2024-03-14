package ru.rakhovetski.juniormath.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDtoResponse {

    private UUID roomId;
    private Integer code;
    private String name;
    private Short classNumber;
    private String createdBy;
    private LocalDateTime createdAt;
}
