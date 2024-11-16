package vk.itmo.teamgray.backend.education.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;

//Will be integrated with VK API, for now only name, but might be a good idea to also add Faculty info.
@Entity
@Table(name = "education_institution")
public class EducationInstitution extends BaseEntity {
    private String name;
}
