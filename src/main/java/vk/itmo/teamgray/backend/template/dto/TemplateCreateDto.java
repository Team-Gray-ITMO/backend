package vk.itmo.teamgray.backend.template.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vk.itmo.teamgray.backend.file.dto.FileDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Template Create Model")
public class TemplateCreateDto {
    @Schema(description = "The name of the template")
    @NotNull
    @NotEmpty
    private String name;

    @Schema(description = "The file associated with the template (ZIP archive with an HTML)")
    private FileDto file;
}
