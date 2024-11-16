package vk.itmo.teamgray.backend.education.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;

//Will be integrated with VK API, for now only name, but might be a good idea to also add Faculty info.
@Getter
@Setter
@Entity
@Table(name = "education_institution")
public class EducationInstitution extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;
}
