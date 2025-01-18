package vk.itmo.teamgray.backend.educationinstitution.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.common.validation.NotBlankOrNull;

@NoArgsConstructor
@Schema(description = "Education Institution Update Model")
@Data
public class EducationInstitutionUpdateDto {
    @Positive
    @Schema(description = "ID of the education institution")
    private long id;

    @Schema(description = "Name of the education institution")
    @NotBlankOrNull
    @Size(max = 255)
    private String name;
}
