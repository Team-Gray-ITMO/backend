package vk.itmo.teamgray.backend.education.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.education.enums.EducationDegreeType;
import vk.itmo.teamgray.backend.educationinstitution.dto.EducationInstitutionDto;

@NoArgsConstructor
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

    @Schema(description = "Education institution subdivision (Faculty, Institute, etc.)")
    private String educationInstitutionSubdivision;

    @Schema(description = "Education format ([rus] ОЧНОЕ, ОЧНО-ЗАОЧНОЕ, ЗАОЧНОЕ)")
    private EducationFormat format;

    @Schema(description = "Education attendance format")
    private EducationAttendanceFormat attendanceFormat;
}
