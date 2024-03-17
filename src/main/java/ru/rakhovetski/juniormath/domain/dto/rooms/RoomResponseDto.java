package ru.rakhovetski.juniormath.domain.dto.rooms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponseDto {

    @JsonProperty("id")
    @Schema(name = "Room id", example = "1")
    private Integer id;

    @JsonProperty("code")
    @Schema(name = "Room code", example = "ASD123")
    private String code;

    @JsonProperty("name")
    @Schema(name = "Room name", example = "Combaski")
    private String name;

    @JsonProperty("class_number")
    @Schema(name = "Room class number", example = "11")
    private Short classNumber;

    @JsonProperty("created_by")
    @Schema(name = "Room created by", example = "stashevskaya123")
    private String createdBy;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(name = "Room created at", example = "2001-01-15:00:00:00")
    private LocalDateTime createdAt;
}
