package vk.itmo.teamgray.backend.education.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.education.enums.EducationDegreeType;

@NoArgsConstructor
@Schema(description = "Education Record Update Model")
@Data
public class EducationUpdateDto {
    @Schema(description = "ID of the education")
    private long id;

    @Schema(description = "ID of the resume associated with this education")
    private Long resumeId;

    @Schema(description = "ID of the education institution")
    private Long educationInstitutionId;

    @Schema(description = "Degree type")
    private EducationDegreeType degreeType;

    @Schema(description = "Name of the degree")
    private String degreeName;

    @Schema(description = "Field of study")
    private String fieldOfStudy;

    @Schema(description = "Specialization within the field")
    private String specialization;

    @Schema(description = "Start date of the education")
    private Date startDate;

    @Schema(description = "End date of the education")
    private Date endDate;

    @Schema(description = "Grade")
    private String grade;
}
