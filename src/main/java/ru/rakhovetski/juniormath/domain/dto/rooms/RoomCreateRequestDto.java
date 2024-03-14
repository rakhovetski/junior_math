package ru.rakhovetski.juniormath.domain.dto.rooms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomCreateRequestDto {
    private String name;
    private Short classNumber;
}
