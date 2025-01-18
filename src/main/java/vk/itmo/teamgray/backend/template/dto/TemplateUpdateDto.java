package vk.itmo.teamgray.backend.template.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vk.itmo.teamgray.backend.common.validation.NotBlankOrNull;
import vk.itmo.teamgray.backend.file.dto.FileDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Template Update Model")
public class TemplateUpdateDto {
    @Positive
    @Schema(description = "The unique identifier of the template")
    private long id;

    @Schema(description = "The name of the template")
    @NotBlankOrNull
    private String name;

    @Valid
    @Schema(description = "The file associated with the template (ZIP archive with an HTML)")
    private FileDto file;
}
