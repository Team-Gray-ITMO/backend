package vk.itmo.teamgray.backend.educationinstitution.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.itmo.teamgray.backend.educationinstitution.entities.EducationInstitution;

public interface EducationInstitutionRepository extends JpaRepository<EducationInstitution, Long> {
}
