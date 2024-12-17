package vk.itmo.teamgray.backend.education.repos;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vk.itmo.teamgray.backend.education.entities.Education;

public interface EducationRepository extends JpaRepository<Education, Long> {
    @Query("""
        SELECT e
        FROM Education e
        JOIN e.resume r
        WHERE e.id = :educationId
        AND r.user.id = :userId
        """)
    Optional<Education> findByIdSecure(
        @Param("educationId") long educationId,
        @Param("userId") long userId
    );

    @Query("""
        SELECT e
        FROM Education e
        JOIN e.resume r
        WHERE r.user.id = :userId
        """)
    List<Education> findAllSecure(@Param("userId") long userId);
}
