package ru.rakhovetski.juniormath.domain.dto.rooms;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomUpdateRequestDto {

    @Size(min = 2, max = 256)
    @JsonProperty("name")
    @Schema(description = "room name", example = "Unified state exam", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String name;

    @Min(value = 1)
    @Max(value = 11)
    @JsonProperty("class_number")
    @Schema(description = "class number", example = "11", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Short classNumber;
}
