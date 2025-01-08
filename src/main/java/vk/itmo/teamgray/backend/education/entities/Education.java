package vk.itmo.teamgray.backend.education.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;
import vk.itmo.teamgray.backend.education.dto.EducationAttendanceFormat;
import vk.itmo.teamgray.backend.education.dto.EducationCreateDto;
import vk.itmo.teamgray.backend.education.dto.EducationFormat;
import vk.itmo.teamgray.backend.education.enums.EducationDegreeType;
import vk.itmo.teamgray.backend.educationinstitution.entities.EducationInstitution;
import vk.itmo.teamgray.backend.resume.entities.Resume;

@Getter
@Setter
@Entity
@Table(name = "education")
@NoArgsConstructor
public class Education extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "education_institution_id", nullable = false)
    private EducationInstitution institution;

    @Column(name = "education_institution_subdivision")
    private String educationInstitutionSubdivision;

    @Column(name = "format")
    @Enumerated(EnumType.STRING)
    private EducationFormat format;

    @Column(name = "attendance_format")
    @Enumerated(EnumType.STRING)
    private EducationAttendanceFormat attendanceFormat;

    @Column(name = "degree_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EducationDegreeType degreeType;

    @Column(name = "degree_name")
    private String degreeName;

    @Column(name = "field_of_study")
    private String fieldOfStudy;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "grade")
    private String grade;

    public Education(EducationCreateDto data, Resume resume, EducationInstitution institution) {
        this.resume = resume;
        this.institution = institution;
        educationInstitutionSubdivision = data.getEducationInstitutionSubdivision();
        format = data.getFormat();
        attendanceFormat = data.getAttendanceFormat();
        degreeType = data.getDegreeType();
        degreeName = data.getDegreeName();
        fieldOfStudy = data.getFieldOfStudy();
        specialization = data.getSpecialization();
        startDate = data.getStartDate();
        endDate = data.getEndDate();
        grade = data.getGrade();
    }
}
