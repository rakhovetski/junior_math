package ru.rakhovetski.juniormath.mapper;

import ru.rakhovetski.juniormath.domain.dto.rooms.RoomsDetailResponseDto;
import ru.rakhovetski.juniormath.entity.Room;

public class RoomDetailsMapper {

    public static RoomsDetailResponseDto map(Room room) {
        return RoomsDetailResponseDto
                .builder()
                .id(room.getId())
                .code(room.getCode())
                .name(room.getName())
                .classNumber(room.getClassNumber())
                .createdBy(room.getCreatedBy())
                .createdAt(room.getCreatedAt())
                .build();
    }
}
