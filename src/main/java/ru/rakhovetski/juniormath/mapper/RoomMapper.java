package ru.rakhovetski.juniormath.mapper;

import ru.rakhovetski.juniormath.domain.dto.rooms.RoomResponseDto;
import ru.rakhovetski.juniormath.entity.Room;

public class RoomMapper {

    public static RoomResponseDto map(Room room) {
        return new RoomResponseDto(room.getId(), room.getCode(), room.getName(),
                room.getClassNumber(), room.getCreatedBy(), room.getCreatedAt());
    }
}
