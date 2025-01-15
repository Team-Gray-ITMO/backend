package vk.itmo.teamgray.backend.education.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.education.enums.EducationAttendanceFormat;
import vk.itmo.teamgray.backend.education.enums.EducationDegreeType;
import vk.itmo.teamgray.backend.education.enums.EducationFormat;

@NoArgsConstructor
@Schema(description = "Education Record Update Model")
@Data
public class EducationUpdateDto {
    @Schema(description = "ID of the education")
    private long id;

    @Schema(description = "ID of the resume associated with this education")
    @NotNull
    @NotEmpty
    private Long resumeId;

    @Schema(description = "ID of the education institution")
    @NotNull
    @NotEmpty
    private Long educationInstitutionId;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 255)
    @Schema(description = "Education institution subdivision (Faculty, Institute, etc.)")
    private String institutionSubdivision;

    @Schema(description = "Education format ([rus] ОЧНОЕ, ОЧНО-ЗАОЧНОЕ, ЗАОЧНОЕ)")
    private EducationFormat format;

    @Schema(description = "Education attendance format")
    private EducationAttendanceFormat attendanceFormat;

    @Schema(description = "Degree type")
    private EducationDegreeType degreeType;

    @Size(max = 255)
    @Schema(description = "Name of the degree")
    private String degreeName;

    @Size(max = 255)
    @Schema(description = "Field of study")
    private String fieldOfStudy;

    @Size(max = 255)
    @Schema(description = "Specialization within the field")
    private String specialization;

    @Schema(description = "Start date of the education")
    private Date startDate;

    @Schema(description = "End date of the education")
    private Date endDate;

    @Size(max = 255)
    @Schema(description = "Grade")
    private String grade;
}
