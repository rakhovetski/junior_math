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
import ru.rakhovetski.juniormath.domain.dto.rooms.*;
import ru.rakhovetski.juniormath.exception.ChangeRoomException;
import ru.rakhovetski.juniormath.exception.RoomNotFoundException;
import ru.rakhovetski.juniormath.exception.UserNotFoundException;
import ru.rakhovetski.juniormath.service.RoomService;

@Slf4j
@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomRestControllerV1 {

    private final RoomService roomService;

    @Operation(
            summary = "Creating a teacher's room.",
            operationId = "createRoom",
            description = "Creating a teacher's room.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = RoomResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - user not found",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = UserNotFoundException.class))
                            }),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = Exception.class))
                            }
                    )
            }
    )
    @PostMapping()
    @PreAuthorize("hasRole('admin')")
    public RoomResponseDto createRoom(@RequestBody RoomCreateRequestDto requestDto,
                                      @AuthenticationPrincipal Jwt token) {
        return roomService.createRoom(requestDto, token);
    }

    @Operation(
            summary = "Find room by code.",
            operationId = "findRoomByCode",
            description = "Find room by code.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = RoomsDetailResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - room not found",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = RoomNotFoundException.class))
                            }),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = DefaultResponseDto.class))
                            }
                    )
            }
    )
    @GetMapping("/{code}")
    public RoomsDetailResponseDto findRoomByCode(@PathVariable("code") String code) {
        RoomCodeRequestDto requestDto = new RoomCodeRequestDto(code);
        return roomService.findRoomByCode(requestDto);
    }

    @Operation(
            summary = "Update room by id.",
            operationId = "updateRoom",
            description = "Update room by id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = RoomResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - room not found",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = RoomNotFoundException.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - the user is not the room owner",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = ChangeRoomException.class))
                            }),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = Exception.class))
                            }
                    )
            }
    )
    @PutMapping("/{code}")
    @PreAuthorize("hasRole('admin')")
    public RoomResponseDto updateRoom(@PathVariable("code") String code,
                                      @RequestBody RoomUpdateRequestDto requestDto,
                                      @AuthenticationPrincipal Jwt token) {
        return roomService.updateRoom(code, requestDto, token);
    }

    @Operation(
            summary = "Delete room by id.",
            operationId = "deleteRoom",
            description = "Delete room by id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = DefaultResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - room not found",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = RoomNotFoundException.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - the user is not the room owner",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = ChangeRoomException.class))
                            }),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = Exception.class))
                            }
                    )
            }
    )
    @DeleteMapping("/{code}")
    @PreAuthorize("hasRole('admin')")
    public DefaultResponseDto deleteRoom(@PathVariable("code") String code,
                                         @AuthenticationPrincipal Jwt token) {
        return roomService.deleteRoom(code, token);
    }
}
