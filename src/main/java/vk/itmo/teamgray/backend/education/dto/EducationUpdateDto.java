package vk.itmo.teamgray.backend.education.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.common.validation.NotBlankOrNull;
import vk.itmo.teamgray.backend.education.enums.EducationAttendanceFormat;
import vk.itmo.teamgray.backend.education.enums.EducationDegreeType;
import vk.itmo.teamgray.backend.education.enums.EducationFormat;

@NoArgsConstructor
@Schema(description = "Education Record Update Model")
@Data
public class EducationUpdateDto {
    @Positive
    @Schema(description = "ID of the education")
    private long id;

    @Schema(description = "ID of the resume associated with this education")
    @Positive
    private Long resumeId;

    @Schema(description = "ID of the education institution")
    @Positive
    private Long educationInstitutionId;

    @NotBlankOrNull
    @Size(min = 1, max = 255)
    @Schema(description = "Education institution subdivision (Faculty, Institute, etc.)")
    private String institutionSubdivision;

    @Schema(description = "Education format ([rus] ОЧНОЕ, ОЧНО-ЗАОЧНОЕ, ЗАОЧНОЕ)")
    private EducationFormat format;

    @Schema(description = "Education attendance format")
    private EducationAttendanceFormat attendanceFormat;

    @Schema(description = "Degree type")
    private EducationDegreeType degreeType;

    @NotBlankOrNull
    @Size(max = 255)
    @Schema(description = "Name of the degree")
    private String degreeName;

    @NotBlankOrNull
    @Size(max = 255)
    @Schema(description = "Field of study")
    private String fieldOfStudy;

    @NotBlankOrNull
    @Size(max = 255)
    @Schema(description = "Specialization within the field")
    private String specialization;

    @Past
    @Schema(description = "Start date of the education")
    private Date startDate;

    @Schema(description = "End date of the education")
    private Date endDate;

    @NotBlankOrNull
    @Size(max = 255)
    @Schema(description = "Grade")
    private String grade;
}
