package ru.rakhovetski.juniormath.http.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Creating a test.",
            operationId = "createTest",
            description = "Creating test.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = TestResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - room not found",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = DefaultResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - the user is not the room owner",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = DefaultResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = DefaultResponseDto.class))
                            }
                    )
            }
    )
    @PostMapping("")
    @PreAuthorize("hasRole('teacher')")
    public TestResponseDto createTest(
            @PathVariable("room_code") String roomCode,
            @RequestBody TestCreateRequestDto requestDto,
            @AuthenticationPrincipal Jwt jwtToken) {
        return testService.createTest(roomCode, requestDto, jwtToken);
    }

    @Operation(
            summary = "Find a test by id.",
            operationId = "findTestById",
            description = "Finding a test by id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = TestResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - room not found",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = DefaultResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - the test is not found",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = DefaultResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = DefaultResponseDto.class))
                            }
                    )
            }
    )
    @GetMapping("/{test_id}")
    public TestDetailResponseDto findTestById(@PathVariable("room_code") String roomCode,
                                              @PathVariable("test_id") Integer testId) {
        return testService.findTestByIdAndRoomCode(roomCode, testId);
    }

    @Operation(
            summary = "Updating a test.",
            operationId = "updateTest",
            description = "Updating a test.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = TestResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - room not found",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = DefaultResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - the test is not found",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = DefaultResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - the user is not the room owner",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = DefaultResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = DefaultResponseDto.class))
                            }
                    )
            }
    )
    @PutMapping("/{test_id}")
    @PreAuthorize("hasRole('teacher')")
    public TestResponseDto updateTest(@PathVariable("room_code") String roomCode,
                                      @PathVariable("test_id") Integer testId,
                                      @RequestBody TestUpdateRequestDto requestDto,
                                      @AuthenticationPrincipal Jwt jwtToken) {
        return testService.updateTest(roomCode, testId, requestDto, jwtToken);
    }

    @Operation(
            summary = "Deleting a test.",
            operationId = "deleteTest",
            description = "Deleting a test.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = TestResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - room not found",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = DefaultResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - the test is not found",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = DefaultResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - the user is not the room owner",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = DefaultResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = DefaultResponseDto.class))
                            }
                    )
            }
    )
    @DeleteMapping("/{test_id}")
    @PreAuthorize("hasRole('teacher')")
    public DefaultResponseDto deleteTest(@PathVariable("room_code") String roomCode,
                                         @PathVariable("test_id") Integer testId,
                                         @AuthenticationPrincipal Jwt jwtToken) {
        return testService.deleteTest(roomCode, testId, jwtToken);
    }
}
