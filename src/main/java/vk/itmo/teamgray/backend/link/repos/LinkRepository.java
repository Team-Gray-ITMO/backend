package vk.itmo.teamgray.backend.link.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.itmo.teamgray.backend.link.entities.Link;

public interface LinkRepository extends JpaRepository<Link, Long> {
}
