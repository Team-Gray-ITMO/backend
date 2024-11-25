package vk.itmo.teamgray.backend.education.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.itmo.teamgray.backend.education.entities.EducationInstitution;

public interface EducationInstitutionRepository extends JpaRepository<EducationInstitution, Long> {
}