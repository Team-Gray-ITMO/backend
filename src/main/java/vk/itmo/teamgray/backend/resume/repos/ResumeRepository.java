package vk.itmo.teamgray.backend.resume.repos;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vk.itmo.teamgray.backend.resume.entities.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    @Query("""
        SELECT r
        FROM Resume r
        LEFT JOIN FETCH r.user
        LEFT JOIN FETCH r.certifications
        LEFT JOIN FETCH r.educations ed
        LEFT JOIN FETCH ed.institution
        LEFT JOIN FETCH r.jobs j
        LEFT JOIN FETCH j.company
        LEFT JOIN FETCH r.links
        LEFT JOIN FETCH r.skills
        WHERE r.id = :resumeId
        AND r.user.id = :userId
        """)
    Optional<Resume> findByIdAndFetch(
        @Param("resumeId") long resumeId,
        @Param("userId") long userId
    );

    @Query("""
        SELECT r
        FROM Resume r
        LEFT JOIN FETCH r.user
        LEFT JOIN FETCH r.certifications
        LEFT JOIN FETCH r.educations ed
        LEFT JOIN FETCH ed.institution
        LEFT JOIN FETCH r.jobs j
        LEFT JOIN FETCH j.company
        LEFT JOIN FETCH r.links
        LEFT JOIN FETCH r.skills
        WHERE r.user.id = :userId
        """)
    List<Resume> findAllAndFetch(@Param("userId") long userId);
}
