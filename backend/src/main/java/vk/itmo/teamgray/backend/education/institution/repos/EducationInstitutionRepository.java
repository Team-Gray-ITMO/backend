package vk.itmo.teamgray.backend.education.institution.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.itmo.teamgray.backend.education.institution.entities.EducationInstitution;

public interface EducationInstitutionRepository extends JpaRepository<EducationInstitution, Long> {
}
