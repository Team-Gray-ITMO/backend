package vk.itmo.teamgray.backend.resume.entities;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import vk.itmo.teamgray.backend.cetification.entities.Certification;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;
import vk.itmo.teamgray.backend.education.entities.Education;
import vk.itmo.teamgray.backend.job.entities.Job;
import vk.itmo.teamgray.backend.job.enums.JobAttendanceFormat;
import vk.itmo.teamgray.backend.language.entities.Language;
import vk.itmo.teamgray.backend.link.entities.Link;
import vk.itmo.teamgray.backend.skill.entities.Skill;
import vk.itmo.teamgray.backend.template.entities.Template;
import vk.itmo.teamgray.backend.user.entities.User;

@Getter
@Setter
@Entity
@Table(name = "resume")
@NoArgsConstructor
public class Resume extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 2000)
    private String summary;

    @Enumerated(EnumType.STRING)
    @Type(ListArrayType.class)
    @Column(name = "preferred_attendance_formats")
    private List<String> preferredAttendanceFormats;

    @Type(ListArrayType.class)
    @Column(name = "preferred_specialities")
    private List<String> preferredSpecialities;

    @Column(name = "ready_for_business_trips")
    private Boolean readyForBusinessTrips;

    @Column(name = "ready_for_relocation")
    private Boolean readyForRelocation;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Job> jobs;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Education> educations;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Skill> skills;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Language> languages;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Certification> certifications;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Link> links;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    @Column(name = "image_path")
    private String imagePath;

    public List<JobAttendanceFormat> getPreferredAttendanceFormats() {
        if (preferredAttendanceFormats == null) {
            return null;
        }

        return preferredAttendanceFormats.stream()
            .map(JobAttendanceFormat::valueOf)
            .toList();
    }

    public void setPreferredAttendanceFormats(List<JobAttendanceFormat> preferredAttendanceFormats) {
        if (preferredAttendanceFormats == null) {
            this.preferredAttendanceFormats = null;

            return;
        }

        this.preferredAttendanceFormats = preferredAttendanceFormats.stream()
            .map(Enum::name)
            .toList();
    }
}
