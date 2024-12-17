package vk.itmo.teamgray.backend.language.repos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vk.itmo.teamgray.backend.language.entities.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    @Query("""
        SELECT l
        FROM Language l
        JOIN l.resume r
        WHERE l.id = :languageId
        AND r.user.id = :userId
        """)
    Optional<Language> findByIdSecure(
        @Param("languageId") long languageId,
        @Param("userId") long userId
    );
}
