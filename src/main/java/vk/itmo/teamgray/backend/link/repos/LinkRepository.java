package vk.itmo.teamgray.backend.link.repos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vk.itmo.teamgray.backend.link.entities.Link;

public interface LinkRepository extends JpaRepository<Link, Long> {
    @Query("""
        SELECT l
        FROM Link l
        JOIN l.resume r
        WHERE l.id = :linkId
        AND r.user.id = :userId
        """)
    Optional<Link> findByIdSecure(
        @Param("linkId") long linkId,
        @Param("userId") long userId
    );
}
