package vk.itmo.teamgray.backend.education.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.itmo.teamgray.backend.education.entities.Education;

public interface EducationRepository extends JpaRepository<Education, Long> {
}