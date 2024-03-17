package ru.rakhovetski.juniormath.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rakhovetski.juniormath.domain.dto.DefaultResponseDto;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskResponseDto;
import ru.rakhovetski.juniormath.domain.dto.tests.TestCreateRequestDto;
import ru.rakhovetski.juniormath.domain.dto.tests.TestDetailResponseDto;
import ru.rakhovetski.juniormath.domain.dto.tests.TestResponseDto;
import ru.rakhovetski.juniormath.domain.dto.tests.TestUpdateRequestDto;
import ru.rakhovetski.juniormath.domain.enums.ErrorCode;
import ru.rakhovetski.juniormath.domain.enums.SuccessCode;
import ru.rakhovetski.juniormath.entity.Room;
import ru.rakhovetski.juniormath.entity.Task;
import ru.rakhovetski.juniormath.entity.TaskTest;
import ru.rakhovetski.juniormath.entity.Test;
import ru.rakhovetski.juniormath.exception.*;
import ru.rakhovetski.juniormath.mapper.TaskMapper;
import ru.rakhovetski.juniormath.mapper.TestDetailMapper;
import ru.rakhovetski.juniormath.mapper.TestMapper;
import ru.rakhovetski.juniormath.repository.RoomRepository;
import ru.rakhovetski.juniormath.repository.TestRepository;
import ru.rakhovetski.juniormath.service.TestService;
import ru.rakhovetski.juniormath.util.DataByJwtUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final RoomRepository roomRepository;


    @Transactional(readOnly = true)
    @Override
    public TestDetailResponseDto findTestByIdAndRoomCode(String roomCode, Integer testId) {
        Room room = findRoomByCode(roomCode);

        Test test = findTestByIdAndRoom(testId, room);
        List<Task> tasks = new ArrayList<>();

        for (TaskTest taskTest : test.getTaskTests()) {
            tasks.add(taskTest.getTask());
        }

        List<TaskResponseDto> taskResponseDtos = TaskMapper.map(tasks);

        log.info("Room find by code - {}, with name {}", roomCode, test.getName());

        return TestDetailMapper.map(test, taskResponseDtos);
    }

    @Override
    public TestResponseDto createTest(String roomCode, TestCreateRequestDto createRequestDto, Jwt jwtToken) {
        Room room = findRoomByCode(roomCode);

        String username = DataByJwtUtil.getUsernameJwtClaim(jwtToken);

        validateRoomCreator(username, room.getCreatedBy());

        Test test = Test.builder()
                .name(createRequestDto.getName())
                .startedAt(createRequestDto.getStartedAt())
                .build();
        test.setRoom(room);

        Test result = testRepository.save(test);

        validateRoomCreatedDate(result.getCreatedAt(), createRequestDto.getStartedAt());

        log.info("The test was successfully created with the name - {}", test.getName());

        return TestMapper.map(result);
    }

    @Override
    public TestResponseDto updateTest(String roomCode, Integer testId, TestUpdateRequestDto createRequestDto, Jwt jwtToken) {
        Room room = findRoomByCode(roomCode);
        Test test = findTestByIdAndRoom(testId, room);
        String username = DataByJwtUtil.getUsernameJwtClaim(jwtToken);

        validateRoomCreator(username, room.getCreatedBy());
        validateRoomCreatedDate(test.getCreatedAt(), createRequestDto.getStartedAt());

        test.setName(Optional.ofNullable(createRequestDto.getName()).orElse(test.getName()));
        test.setStartedAt(Optional.ofNullable(createRequestDto.getStartedAt()).orElse(test.getStartedAt()));

        Test result = testRepository.save(test);

        log.info("The test was successfully updated with the name - {}", result.getName());

        return TestMapper.map(result);
    }

    @Override
    public DefaultResponseDto deleteTest(String roomCode, Integer testId, Jwt jwtToken) {
        Room room = findRoomByCode(roomCode);
        String username = DataByJwtUtil.getUsernameJwtClaim(jwtToken);

        validateRoomCreator(username, room.getCreatedBy());

        Test test = findTestByIdAndRoom(testId, room);

        testRepository.delete(test);

        log.info("The test was successfully deleted with the name - {}", test.getName());

        return new DefaultResponseDto(SuccessCode.ROOM_SUCCESS_DELETED.getMessage(),
                HttpStatus.NO_CONTENT.name(),
                LocalDateTime.now());
    }


    private Room findRoomByCode(String code) {
        return roomRepository.findByCode(code).orElseThrow(
                () -> {
                    log.error("Error - the room was not found by code");
                    return new RoomNotFoundException(ErrorCode.ROOM_NOT_FOUND.getMessage());
                }
        );
    }

    private Test findTestByIdAndRoom(Integer id, Room room) {
        return testRepository.findByRoomAndId(room, id).orElseThrow(
                () -> {
                    log.error("Error - the test was not found by id");
                    return new TestNotFoundException(ErrorCode.TEST_DOES_NOT_EXISTS.getMessage());
                }
        );
    }

    private void validateRoomCreator(String requestUsername, String roomCreatedBy) {
        if (!requestUsername.equals(roomCreatedBy)) {
            log.error("Error - the creator of the room does not match the current teacher");
            throw new ChangeTestException(ErrorCode.TEACHER_CANNOT_CHANGE_TEST.getMessage());
        }
    }

    private void validateRoomCreatedDate(LocalDateTime createdAt, LocalDateTime startedAt) {
        if (createdAt.isAfter(startedAt)) {
            log.error("Error - the creation date is greater than the start date");
            throw new CreationDateGreaterStartDateException(ErrorCode.CREATED_AT_GREATER_STARTED_AT.getMessage());
        }
    }
}
