package ru.rakhovetski.juniormath.integrational.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.jdbc.Sql;
import ru.rakhovetski.juniormath.domain.dto.DefaultResponseDto;
import ru.rakhovetski.juniormath.domain.dto.rooms.*;
import ru.rakhovetski.juniormath.domain.dto.users.UserResponseDto;
import ru.rakhovetski.juniormath.entity.User;
import ru.rakhovetski.juniormath.exception.ChangeRoomException;
import ru.rakhovetski.juniormath.exception.RoomNotFoundException;
import ru.rakhovetski.juniormath.exception.UserNotFoundException;
import ru.rakhovetski.juniormath.integrational.IntegrationBaseTest;
import ru.rakhovetski.juniormath.integrational.util.JwtGenerator;
import ru.rakhovetski.juniormath.integrational.util.RoomDataGenerator;
import ru.rakhovetski.juniormath.service.RoomService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Sql({
        "/db/data.sql"
})
public class RoomServiceTest extends IntegrationBaseTest {

    @Autowired
    private RoomService roomService;

    @Test
    public void testCreateRoomCorrectData() {
        String createdBy = "username";

        RoomCreateRequestDto createRequestDto = RoomDataGenerator.generateCreateRoom();
        Jwt jwt = JwtGenerator.generateJwt(createdBy);

        RoomResponseDto result = roomService.createRoom(createRequestDto, jwt);

        assertEquals(createRequestDto.getName(), result.getName());
        assertEquals(createRequestDto.getClassNumber(), result.getClassNumber());
        assertEquals(createdBy, result.getCreatedBy());
    }

    @Test
    public void testCreateRoomIncorrectUsername() {
        String createdBy = "username-incorrect";

        RoomCreateRequestDto createRequestDto = RoomDataGenerator.generateCreateRoom();
        Jwt jwt = JwtGenerator.generateJwt(createdBy);

        assertThrows(UserNotFoundException.class, () -> roomService.createRoom(createRequestDto, jwt));
    }

    @Test
    public void testUpdateRoomCorrectData() {
        String createdBy = "username";

        RoomCreateRequestDto createRequestDto = RoomDataGenerator.generateCreateRoom();
        Jwt jwt = JwtGenerator.generateJwt(createdBy);

        RoomResponseDto createResult = roomService.createRoom(createRequestDto, jwt);

        RoomUpdateRequestDto updateRequestDto = RoomDataGenerator.generateUpdateRoom();

        RoomResponseDto updateResult = roomService.updateRoom(createResult.getId(), updateRequestDto, jwt);

        assertEquals(updateRequestDto.getName(), updateResult.getName());
        assertEquals(updateRequestDto.getClassNumber(), updateResult.getClassNumber());
        assertEquals(createdBy, updateResult.getCreatedBy());
    }

    @Test
    public void testUpdateRoomWithNoData() {
        String createdBy = "username";

        RoomCreateRequestDto createRequestDto = RoomDataGenerator.generateCreateRoom();
        Jwt jwt = JwtGenerator.generateJwt(createdBy);

        RoomResponseDto createResult = roomService.createRoom(createRequestDto, jwt);

        RoomUpdateRequestDto updateRequestDto = RoomDataGenerator.generateUpdateRoom();
        updateRequestDto.setName(null);
        updateRequestDto.setClassNumber(null);

        RoomResponseDto updateResult = roomService.updateRoom(createResult.getId(), updateRequestDto, jwt);

        assertEquals(createResult.getName(), updateResult.getName());
        assertEquals(createResult.getClassNumber(), updateResult.getClassNumber());
        assertEquals(createdBy, updateResult.getCreatedBy());
    }

    @Test
    public void testUpdateRoomIncorrectUsername() {
        String createdBy = "username";

        RoomCreateRequestDto createRequestDto = RoomDataGenerator.generateCreateRoom();
        Jwt jwt = JwtGenerator.generateJwt(createdBy);

        RoomResponseDto createResult = roomService.createRoom(createRequestDto, jwt);

        String incorrectCreatedBy = "username-incorrect";
        Jwt jwt1 = JwtGenerator.generateJwt(incorrectCreatedBy);

        RoomUpdateRequestDto updateRequestDto = RoomDataGenerator.generateUpdateRoom();

        assertThrows(ChangeRoomException.class, () -> roomService.updateRoom(createResult.getId(), updateRequestDto, jwt1));
    }

    @Test
    public void testUpdateRoomIncorrectRoomId() {
        String createdBy = "username";

        RoomCreateRequestDto createRequestDto = RoomDataGenerator.generateCreateRoom();
        Jwt jwt = JwtGenerator.generateJwt(createdBy);

        roomService.createRoom(createRequestDto, jwt);

        RoomUpdateRequestDto updateRequestDto = RoomDataGenerator.generateUpdateRoom();
        Integer roomId = 1_000_000;

        assertThrows(RoomNotFoundException.class, () -> roomService.updateRoom(roomId, updateRequestDto, jwt));
    }

    @Test
    public void testDeleteRoomCorrectId() {
        String createdBy = "username";

        RoomCreateRequestDto createRequestDto = RoomDataGenerator.generateCreateRoom();
        Jwt jwt = JwtGenerator.generateJwt(createdBy);

        RoomResponseDto createResult = roomService.createRoom(createRequestDto, jwt);

        DefaultResponseDto responseDto = roomService.deleteRoom(createResult.getId(), jwt);

        assertEquals(HttpStatus.NO_CONTENT.name(), responseDto.getStatus());
    }

    @Test
    public void testDeleteRoomIncorrectId() {
        String createdBy = "username";

        RoomCreateRequestDto createRequestDto = RoomDataGenerator.generateCreateRoom();
        Jwt jwt = JwtGenerator.generateJwt(createdBy);

        RoomResponseDto createResult = roomService.createRoom(createRequestDto, jwt);

        Integer incorrectRoomId = 1_000_000;

        assertThrows(RoomNotFoundException.class, () -> roomService.deleteRoom(incorrectRoomId, jwt));
    }

    @Test
    public void testDeleteRoomIncorrectUsername() {
        String createdBy = "username";

        RoomCreateRequestDto createRequestDto = RoomDataGenerator.generateCreateRoom();
        Jwt jwt = JwtGenerator.generateJwt(createdBy);

        RoomResponseDto createResult = roomService.createRoom(createRequestDto, jwt);

        String incorrectUsername = "username-incorrect";
        Jwt jwt1 = JwtGenerator.generateJwt(incorrectUsername);

        assertThrows(ChangeRoomException.class, () -> roomService.deleteRoom(createResult.getId(), jwt1));
    }

    @Test
    public void testFindRoomByCode() {
        String createdBy = "username";

        RoomCreateRequestDto createRequestDto = RoomDataGenerator.generateCreateRoom();
        Jwt jwt = JwtGenerator.generateJwt(createdBy);

        RoomResponseDto createResult = roomService.createRoom(createRequestDto, jwt);

        RoomCodeRequestDto codeRequestDto = new RoomCodeRequestDto(createResult.getCode());
        RoomsDetailResponseDto response = roomService.findRoomByCode(codeRequestDto);

        List<UserResponseDto> users = response.getUsers();

        assertEquals(1, users.size());
        assertEquals(createdBy, users.getFirst().getUsername());

        assertEquals(createResult.getCode(), response.getCode());
        assertEquals(createResult.getName(), response.getName());
        assertEquals(createdBy, response.getCreatedBy());
    }

    @Test
    public void testFindRoomByIncorrectCode() {
        String createdBy = "username";

        RoomCreateRequestDto createRequestDto = RoomDataGenerator.generateCreateRoom();
        Jwt jwt = JwtGenerator.generateJwt(createdBy);

        roomService.createRoom(createRequestDto, jwt);

        RoomCodeRequestDto codeRequestDto = new RoomCodeRequestDto("111111");
        assertThrows(RoomNotFoundException.class, () -> roomService.findRoomByCode(codeRequestDto));
    }
}
