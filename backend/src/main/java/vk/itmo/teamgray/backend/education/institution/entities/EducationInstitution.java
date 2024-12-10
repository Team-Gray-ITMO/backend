package vk.itmo.teamgray.backend.education.institution.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vk.itmo.teamgray.backend.common.entities.BaseEntity;
import vk.itmo.teamgray.backend.education.institution.dto.EducationInstitutionCreateDto;
import vk.itmo.teamgray.backend.education.institution.dto.EducationInstitutionUpdateDto;

//Will be integrated with VK API, for now only name, but might be a good idea to also add Faculty info.
@Getter
@Setter
@Entity
@Table(name = "education_institution")
@NoArgsConstructor
public class EducationInstitution extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    public EducationInstitution(EducationInstitutionCreateDto data) {
        name = data.getName();
    }

    public EducationInstitution(EducationInstitutionUpdateDto data) {
        id = data.getId();
        name = data.getName();
    }
}
