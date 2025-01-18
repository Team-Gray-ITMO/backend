package vk.itmo.teamgray.backend.cetification.repos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vk.itmo.teamgray.backend.cetification.entities.Certification;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
    @Query("""
        SELECT c
        FROM Certification c
        JOIN c.resume r
        WHERE c.id = :certificationId
        AND r.user.id = :userId
        """)
    Optional<Certification> findByIdSecure(
        @Param("certificationId") long certificationId,
        @Param("userId") long userId
    );
}
