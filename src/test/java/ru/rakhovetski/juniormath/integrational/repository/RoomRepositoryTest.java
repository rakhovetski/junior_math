package ru.rakhovetski.juniormath.integrational.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.rakhovetski.juniormath.entity.Room;
import ru.rakhovetski.juniormath.integrational.IntegrationBaseTest;
import ru.rakhovetski.juniormath.repository.RoomRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Sql({
        "/db/data.sql"
})
public class RoomRepositoryTest extends IntegrationBaseTest {

    @Autowired
    private RoomRepository roomRepository;

    @Test
    public void testFindByCodeCorrect() {
        String roomCode = "ASD123";

        String roomName = "room-name1";
        Short classNumber = 9;
        String createdBy = "username";

        Room room = roomRepository.findByCode(roomCode).get();

        assertEquals(roomCode, room.getCode());
        assertEquals(roomName, room.getName());
        assertEquals(classNumber, room.getClassNumber());
        assertEquals(createdBy, room.getCreatedBy());
    }
}
