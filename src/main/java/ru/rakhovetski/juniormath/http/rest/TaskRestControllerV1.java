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
import ru.rakhovetski.juniormath.domain.dto.*;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskCreateRequestDto;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskFilterDto;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskResponseDto;
import ru.rakhovetski.juniormath.domain.dto.tasks.TaskUpdateRequestDto;
import ru.rakhovetski.juniormath.service.TaskService;

@Slf4j
@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskRestControllerV1 {

    private final TaskService taskService;

    @Operation(
            summary = "Find all tasks with parameters and pagination.",
            operationId = "findAllWitPagination",
            description = "Find all tasks with parameters and pagination.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = PageResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = DefaultResponseDto.class))
                            }
                    )
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('teacher')")
    public PageResponseDto<TaskResponseDto> findAllWithPagination(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestBody TaskFilterDto taskFilter
    ) {
        log.info("The request has been received to receive tasks");
        return taskService.findAllTasksWithPagination(taskFilter, page, size);
    }


    @Operation(
            summary = "Find tasks by id.",
            operationId = "findTaskById",
            description = "Find task by id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = TaskResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - incorrect task id",
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
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('teacher')")
    public TaskResponseDto findTaskById(
            @PathVariable("id") Integer id
    ) {
        log.info("The request has been received for a task");
        return taskService.findTaskById(id);
    }


    @Operation(
            summary = "Create task.",
            operationId = "createTask",
            description = "Create task.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = TaskResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - incorrect subject id",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = DefaultResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - incorrect teacher id",
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
    @PostMapping
    @PreAuthorize("hasRole('teacher')")
    public TaskResponseDto createTask(
            @RequestBody TaskCreateRequestDto requestDto,
            @AuthenticationPrincipal Jwt token
    ) {
        log.info("The request has been received to create a task with data");
        return taskService.createTask(requestDto, token);
    }


    @Operation(
            summary = "Update task.",
            operationId = "updateTask",
            description = "Update task.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = TaskResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - incorrect task id",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = DefaultResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - the user is not the task owner",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = DefaultResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - incorrect subject id",
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
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('teacher')")
    public TaskResponseDto updateTask(
            @PathVariable Integer id,
            @RequestBody TaskUpdateRequestDto requestDto,
            @AuthenticationPrincipal Jwt token
    ) {
        log.info("The request has been received to update a task with data");
        return taskService.updateTask(id, requestDto, token);
    }


    @Operation(
            summary = "Delete task.",
            operationId = "deleteTask",
            description = "Delete task.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = TaskResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - incorrect task id",
                            content = {
                                    @Content(mediaType = "application/json", schema =
                                    @Schema(implementation = DefaultResponseDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad request - the user is not the task owner",
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
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('teacher')")
    public DefaultResponseDto deleteTask(
            @PathVariable Integer id,
            @AuthenticationPrincipal Jwt token
    ) {
        log.info("The request has been received to delete a task");
        return taskService.deleteTask(id, token);
    }
}
