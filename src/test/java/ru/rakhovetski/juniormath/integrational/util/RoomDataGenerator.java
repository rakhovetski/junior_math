package ru.rakhovetski.juniormath.integrational.util;

import ru.rakhovetski.juniormath.domain.dto.rooms.RoomCreateRequestDto;
import ru.rakhovetski.juniormath.domain.dto.rooms.RoomUpdateRequestDto;

public class RoomDataGenerator {

    public static RoomCreateRequestDto generateCreateRoom() {
        return new RoomCreateRequestDto(
                "test-room-1",
                (short) 9
        );
    }

    public static RoomUpdateRequestDto generateUpdateRoom() {
        return new RoomUpdateRequestDto(
                "test-update",
                (short) 10
        );
    }
}
