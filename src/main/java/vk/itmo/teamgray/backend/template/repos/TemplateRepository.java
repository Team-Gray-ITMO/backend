package vk.itmo.teamgray.backend.template.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.itmo.teamgray.backend.template.entities.Template;

public interface TemplateRepository extends JpaRepository<Template, Long> {
}
