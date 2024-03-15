package ru.rakhovetski.juniormath.domain.dto.rooms;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rakhovetski.juniormath.domain.dto.tests.TestResponseDto;
import ru.rakhovetski.juniormath.domain.dto.users.UserResponseDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomsDetailResponseDto {

    @JsonProperty("id")
    @Schema(name = "Room id", example = "1")
    private Integer id;

    @JsonProperty("code")
    @Schema(name = "Room code", example = "ASD123")
    private String code;

    @JsonProperty("name")
    @Schema(name = "Room name", example = "Colbaski")
    private String name;

    @JsonProperty("class_number")
    @Schema(name = "Room class number", example = "11")
    private Short classNumber;

    @JsonProperty("created_by")
    @Schema(name = "Room created by", example = "stashevskaya123")
    private String createdBy;

    @JsonProperty("created_at")
    @Schema(name = "Room created at", example = "2001-01-15:00:00:00")
    private LocalDateTime createdAt;

    @JsonProperty("users")
    @Schema(name = "Room users", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<UserResponseDto> users;

    @JsonProperty("tests")
    @Schema(name = "Room tests", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<TestResponseDto> tests;
}
