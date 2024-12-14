package vk.itmo.teamgray.backend.template.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Schema(description = "Template Base Model")
public class TemplateBaseDto {

    @Schema(description = "The ID of the template")
    private long id;

    @Schema(description = "The creation date of the template")
    private Date createdAt;

    @Schema(description = "The name of the template")
    private String name;

    @Schema(description = "The file path in S3 associated with the template")
    private String filePath;
}
