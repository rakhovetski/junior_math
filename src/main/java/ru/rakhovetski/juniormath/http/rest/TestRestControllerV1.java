package ru.rakhovetski.juniormath.http.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.rakhovetski.juniormath.domain.dto.DefaultResponseDto;
import ru.rakhovetski.juniormath.domain.dto.tests.TestCreateRequestDto;
import ru.rakhovetski.juniormath.domain.dto.tests.TestDetailResponseDto;
import ru.rakhovetski.juniormath.domain.dto.tests.TestResponseDto;
import ru.rakhovetski.juniormath.domain.dto.tests.TestUpdateRequestDto;
import ru.rakhovetski.juniormath.service.TestService;

@Slf4j
@RestController
@RequestMapping("/api/v1/rooms/{room_code}/tests")
@RequiredArgsConstructor
public class TestRestControllerV1 {

    private final TestService testService;

    @PostMapping("")
    @PreAuthorize("hasRole('teacher')")
    public TestResponseDto createTest(@PathVariable("room_code") String roomCode,
                                      @RequestBody TestCreateRequestDto requestDto,
                                      @AuthenticationPrincipal Jwt jwtToken) {
        return testService.createTest(roomCode, requestDto, jwtToken);
    }

    @GetMapping("/{test_id}")
    public TestDetailResponseDto findTestById(@PathVariable("room_code") String roomCode,
                                              @PathVariable("test_id") Integer testId) {
        return testService.findTestByIdAndRoomCode(roomCode, testId);
    }

    @PutMapping("/{test_id}")
    @PreAuthorize("hasRole('teacher')")
    public TestResponseDto updateTest(@PathVariable("room_code") String roomCode,
                                      @PathVariable("test_id") Integer testId,
                                      @RequestBody TestUpdateRequestDto requestDto,
                                      @AuthenticationPrincipal Jwt jwtToken) {
        return testService.updateTest(roomCode, testId, requestDto, jwtToken);
    }

    @DeleteMapping("/{test_id}")
    @PreAuthorize("hasRole('teacher')")
    public DefaultResponseDto deleteTest(@PathVariable("room_code") String roomCode,
                                         @PathVariable("test_id") Integer testId,
                                         @AuthenticationPrincipal Jwt jwtToken) {
        return testService.deleteTest(roomCode, testId, jwtToken);
    }
}
