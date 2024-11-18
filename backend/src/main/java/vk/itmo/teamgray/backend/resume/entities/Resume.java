package vk.itmo.teamgray.backend.resume.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vk.itmo.teamgray.backend.cetification.entities.Certification;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;
import vk.itmo.teamgray.backend.education.entities.Education;
import vk.itmo.teamgray.backend.job.entities.Job;
import vk.itmo.teamgray.backend.language.entities.Language;
import vk.itmo.teamgray.backend.resume.dto.ResumeCreateDto;
import vk.itmo.teamgray.backend.resume.dto.ResumeUpdateDto;
import vk.itmo.teamgray.backend.skill.entities.Skill;
import vk.itmo.teamgray.backend.user.entities.User;

@Getter
@Setter
@Entity
@Table(name = "resume")
@NoArgsConstructor
public class Resume extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 2000)
    private String summary;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job> jobs;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> educations;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Language> languages;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Certification> certifications;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Link> links;

    public Resume(ResumeCreateDto data){
        summary = data.summary();
    }

    public Resume(ResumeUpdateDto data){
        id = data.id();
        summary = data.summary();
    }
}
