package vk.itmo.teamgray.backend.education.dto;

import java.util.Date;
import lombok.Data;
import vk.itmo.teamgray.backend.education.enums.EducationDegreeType;

@Data
public class EducationDto {
    private long id;
    private EducationDegreeType degreeType;
    private String degreeName;
    private String fieldOfStudy;
    private String specialization;
    private Date startDate;
    private Date endDate;
    private String grade;
    private EducationInstitutionDto educationInstitution;
}
