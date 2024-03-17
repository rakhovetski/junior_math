package ru.rakhovetski.juniormath.service;

import org.springframework.security.oauth2.jwt.Jwt;
import ru.rakhovetski.juniormath.domain.dto.DefaultResponseDto;
import ru.rakhovetski.juniormath.domain.dto.tests.TestCreateRequestDto;
import ru.rakhovetski.juniormath.domain.dto.tests.TestDetailResponseDto;
import ru.rakhovetski.juniormath.domain.dto.tests.TestResponseDto;
import ru.rakhovetski.juniormath.domain.dto.tests.TestUpdateRequestDto;

import java.util.List;

public interface TestService {

    TestDetailResponseDto findTestByIdAndRoomCode(String roomCode, Integer testId);

    TestResponseDto createTest(String roomCode, TestCreateRequestDto createRequestDto, Jwt jwtToken);

    TestResponseDto updateTest(String roomCode, Integer testId, TestUpdateRequestDto createRequestDto, Jwt jwtToken);

    DefaultResponseDto deleteTest(String roomCode, Integer testId, Jwt jwtToken);
}
