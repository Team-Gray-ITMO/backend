package vk.itmo.teamgray.backend.education.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;
import vk.itmo.teamgray.backend.education.dto.EducationDegree;
import vk.itmo.teamgray.backend.resume.entities.Resume;

@Entity
@Table(name = "education")
public class Education extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @ManyToOne
    @JoinColumn(name = "education_institution_id", nullable = false)
    private EducationInstitution institution;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private EducationDegree degree;

    @Column
    private String degreeName;

    @Column
    private String fieldOfStudy;

    @Column
    private String specialization;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private String grade;
}
