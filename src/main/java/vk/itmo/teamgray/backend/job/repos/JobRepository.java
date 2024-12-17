package vk.itmo.teamgray.backend.job.repos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vk.itmo.teamgray.backend.job.entities.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("""
        SELECT j
        FROM Job j
        JOIN j.resume r
        WHERE j.id = :jobId
        AND r.user.id = :userId
        """)
    Optional<Job> findByIdSecure(
        @Param("jobId") long jobId,
        @Param("userId") long userId
    );
}
