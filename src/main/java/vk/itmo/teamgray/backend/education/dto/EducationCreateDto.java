package vk.itmo.teamgray.backend.education.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.education.enums.EducationAttendanceFormat;
import vk.itmo.teamgray.backend.education.enums.EducationDegreeType;
import vk.itmo.teamgray.backend.education.enums.EducationFormat;

@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Education Record Create Model")
@Data
public class EducationCreateDto {
    @NotNull
    @Positive
    @Schema(description = "Resume ID")
    private Long resumeId;

    @NotNull
    @NotBlank
    @Schema(description = "Education institution ID")
    private Long educationInstitutionId;

    @Size(min = 1, max = 255)
    @Schema(description = "Education institution subdivision (Faculty, Institute, etc.)")
    private String institutionSubdivision;

    @Schema(description = "Education format ([rus] ОЧНОЕ, ОЧНО-ЗАОЧНОЕ, ЗАОЧНОЕ)")
    private EducationFormat format;

    @Schema(description = "Education attendance format")
    private EducationAttendanceFormat attendanceFormat;

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
