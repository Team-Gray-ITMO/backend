package vk.itmo.teamgray.backend.resume.entities;

import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//TODO Only a mockup, needs debugging
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    @Query(
        value =
            """
                SELECT jsonb_build_object(
                               'id', res.id,
                               'createdAt', res.created_at,
                               'summary', res.summary,
                               'user', jsonb_build_object(
                                       'username', us.username,
                                       'email', us.email,
                                       'password', us.password,
                                       'vkId', us.vk_id
                                       ),
                               'certifications', jsonb_agg(
                                       DISTINCT jsonb_build_object(
                                        'name', cert.name,
                                        'issuingOrganization', cert.issuing_organization,
                                        'issueDate', cert.issue_date,
                                        'expirationDate', cert.expiration_date,
                                        'credentialUrl', cert.credential_url,
                                        'languageProficiency', cert.language_proficiency
                                                )
                                                 ),
                               'education', jsonb_agg(
                                       DISTINCT jsonb_build_object(
                                        'degree', edu.degree_type,
                                        'degreeName', edu.degree_name,
                                        'fieldOfStudy', edu.field_of_study,
                                        'specialization', edu.specialization,
                                        'startDate', edu.start_date,
                                        'endDate', edu.end_date,
                                        'grade', edu.grade,
                                        'educationInstitution', ed_inst.name
                                                )
                                            ),
                               'jobs', jsonb_agg(
                                       DISTINCT jsonb_build_object(
                                        'title', j.title,
                                        'company', com.name,
                                        'location', j.location,
                                        'startDate', j.start_date,
                                        'endDate', j.end_date,
                                        'description', j.description
                                                )
                                       ),
                               'links', jsonb_agg(
                                       DISTINCT jsonb_build_object(
                                        'platformName', lin.platform_name,
                                        'profileUrl', lin.profile_url
                                                )
                                        ),
                               'skills', jsonb_agg(
                                       DISTINCT jsonb_build_object(
                                        'name', sk.name,
                                        'proficiency', sk.proficiency
                                                )
                                         ),
                               'languages', jsonb_agg(
                                       DISTINCT jsonb_build_object(
                                        'name', lang.name,
                                        'proficiency', lang.proficiency
                                                )
                                            )
                       )
                FROM resume res
                         JOIN users us ON res.user_id = us.id
                         LEFT JOIN certification cert ON res.id = cert.resume_id
                         LEFT JOIN education edu ON res.id = edu.resume_id
                         LEFT JOIN education_institution ed_inst ON edu.education_institution_id = ed_inst.id
                         LEFT JOIN job j ON res.id = j.resume_id
                         LEFT JOIN company com ON j.company_id = com.id
                         LEFT JOIN languages lang ON res.id = lang.resume_id
                         LEFT JOIN link lin ON res.id = lin.resume_id
                         LEFT JOIN skill sk ON res.id = sk.resume_id
                WHERE res.id = :resumeId
                GROUP BY res.id, us.id;
                """,
        nativeQuery = true
    )
    Map<String, Object> getResumeJson(@Param("resumeId") long resumeId);
}
