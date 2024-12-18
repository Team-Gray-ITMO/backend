package vk.itmo.teamgray.backend.template.repos;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vk.itmo.teamgray.backend.template.entities.Template;

public interface TemplateRepository extends JpaRepository<Template, Long> {
    @Query("""
        SELECT DISTINCT t.fileHash
        FROM Template t
        """)
    Set<String> getAllTemplateHashes();
}
