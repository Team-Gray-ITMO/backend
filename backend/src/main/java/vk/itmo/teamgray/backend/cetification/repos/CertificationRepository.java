package vk.itmo.teamgray.backend.cetification.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.itmo.teamgray.backend.cetification.entities.Certification;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
}