package vk.itmo.teamgray.backend.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import vk.itmo.teamgray.backend.common.validation.NotBlankOrNull;

@NoArgsConstructor
@Schema(description = "Company Update Model")
@Data
public class CompanyUpdateDto {
    @Positive
    @Schema(description = "ID of the company")
    private long id;

    @Schema(description = "Name of the company")
    @NotBlankOrNull
    @Size(max = 255)
    private String name;

    @URL
    @Schema(description = "Company URL")
    @Size(max = 255)
    private String url;
}
