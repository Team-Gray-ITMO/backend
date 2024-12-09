package vk.itmo.teamgray.backend.education.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.Data;
import vk.itmo.teamgray.backend.education.enums.EducationDegreeType;

@Schema(description = "Education Record Model")
@Data
public class EducationDto {
    @Schema(description = "Education ID")
    private long id;

    @Schema(description = "Degree type")
    private EducationDegreeType degreeType;

    @Schema(description = "Degree name")
    private String degreeName;

    @Schema(description = "Field of study")
    private String fieldOfStudy;

    @Schema(description = "Specialization")
    private String specialization;

    @Schema(description = "Start date of the education")
    private Date startDate;

    @Schema(description = "End date of the education")
    private Date endDate;

    @Schema(description = "Grade obtained")
    private String grade;

    @Schema(description = "Education institution details")
    private EducationInstitutionDto institution;
}
