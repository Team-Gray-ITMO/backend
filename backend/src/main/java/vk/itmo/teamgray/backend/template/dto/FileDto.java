package vk.itmo.teamgray.backend.template.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "File Model")
public class FileDto {
    @Schema(description = "The name of the file")
    private String filename;

    @Schema(description = "The content type of the file")
    private String contentType;

    @Schema(description = "The binary content of the file")
    private byte[] content;
}
