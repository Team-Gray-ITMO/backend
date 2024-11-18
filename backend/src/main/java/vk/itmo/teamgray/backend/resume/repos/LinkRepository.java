package vk.itmo.teamgray.backend.resume.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.itmo.teamgray.backend.resume.entities.Link;

public interface LinkRepository extends JpaRepository<Link, Long> {
}