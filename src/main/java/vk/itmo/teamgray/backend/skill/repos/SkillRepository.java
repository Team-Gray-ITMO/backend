package vk.itmo.teamgray.backend.skill.repos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vk.itmo.teamgray.backend.skill.entities.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    @Query("""
        SELECT s
        FROM Skill s
        JOIN s.resume r
        WHERE s.id = :skillId
        AND r.user.id = :userId
        """)
    Optional<Skill> findByIdSecure(
        @Param("skillId") long skillId,
        @Param("userId") long userId
    );
}
