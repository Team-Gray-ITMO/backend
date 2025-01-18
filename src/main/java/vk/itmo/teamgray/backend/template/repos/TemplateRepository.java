package vk.itmo.teamgray.backend.template.repos;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vk.itmo.teamgray.backend.template.entities.Template;

public interface TemplateRepository extends JpaRepository<Template, Long> {
    @Query("""
        SELECT DISTINCT t.fileHash
        FROM Template t
        """)
    Set<String> getAllTemplateHashes();

    @Query("""
        SELECT DISTINCT t.filePath
        FROM Template t
        """)
    Set<String> getAllTemplateFilePaths();

    @Modifying
    @Query("""
        DELETE
        FROM Template t
        WHERE t.filePath LIKE :filePath
        """)
    void dropTemplateByFilePath(
        @Param("filePath") String filePath
    );
}
