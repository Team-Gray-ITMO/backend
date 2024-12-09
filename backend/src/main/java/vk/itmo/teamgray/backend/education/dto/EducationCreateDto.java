package vk.itmo.teamgray.backend.education.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.education.enums.EducationDegreeType;

@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Education Record Create Model")
@Data
public class EducationCreateDto {
    @NotNull
    @Schema(description = "Resume ID")
    private Long resumeId;

    @NotNull
    @Schema(description = "Education institution ID")
    private Long educationInstitutionId;

    @NotNull
    @Schema(description = "Degree type")
    private EducationDegreeType degreeType;

    @NotNull
    @Size(max = 255)
    @Schema(description = "Name of the degree")
    private String degreeName;

    @Size(max = 255)
    @Schema(description = "Field of study")
    private String fieldOfStudy;

    @Size(max = 255)
    @Schema(description = "Specialization")
    private String specialization;

    @Schema(description = "Start date of the education")
    private Date startDate;

    @Schema(description = "End date of the education")
    private Date endDate;

    @Size(max = 255)
    @Schema(description = "Grade")
    private String grade;
}
