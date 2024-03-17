package ru.rakhovetski.juniormath.domain.dto.rooms;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDtoResponse {

    @JsonProperty("room_id")
    private UUID roomId;

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("name")
    private String name;

    @JsonProperty("class_number")
    private Short classNumber;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
