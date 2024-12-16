package vk.itmo.teamgray.backend.resume.repos;

import java.util.List;
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
        """)
    Resume getResume(@Param("resumeId") long resumeId);

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
        """)
    List<Resume> findAllAndFetch();
}
