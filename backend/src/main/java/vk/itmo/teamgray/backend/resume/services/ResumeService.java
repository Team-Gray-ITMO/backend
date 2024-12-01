package vk.itmo.teamgray.backend.resume.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Comparator;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.cetification.dto.CertificationDto;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.education.dto.EducationDto;
import vk.itmo.teamgray.backend.job.dto.JobDto;
import vk.itmo.teamgray.backend.language.dto.LanguageDto;
import vk.itmo.teamgray.backend.resume.dto.LinkDto;
import vk.itmo.teamgray.backend.resume.dto.ResumeCreateDto;
import vk.itmo.teamgray.backend.resume.dto.ResumeDto;
import vk.itmo.teamgray.backend.resume.dto.ResumeUpdateDto;
import vk.itmo.teamgray.backend.resume.entities.Resume;
import vk.itmo.teamgray.backend.resume.mapper.ResumeMapper;
import vk.itmo.teamgray.backend.resume.repos.ResumeRepository;
import vk.itmo.teamgray.backend.skill.dto.SkillDto;
import vk.itmo.teamgray.backend.user.repos.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeService {
    private final ResumeRepository resumeRepository;

    private final UserRepository userRepository;

    public List<Resume> findAll(){
        return resumeRepository.findAll();
    }

    private final ResumeMapper resumeMapper;

    private final ObjectMapper objectMapper;

    public ResumeDto getDTOById(Long id) {
        return resumeMapper.toDto(resumeRepository.findById(id).orElseThrow(ModelNotFoundException::new));
    }

    public Resume findById(Long id) {
        return resumeRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public Resume createResume(ResumeCreateDto data) {
        //TODO Maybe resolve user from auth context.
        var user = userRepository.findById(data.userId())
            .orElseThrow(ModelNotFoundException::new);

        return resumeRepository.save(
            new Resume(
                data,
                user
            )
        );
    }

    public Resume updateResume(ResumeUpdateDto data) {
        return resumeRepository.save(
            new Resume(
                data
            )
        );
    }

    public void deleteById(Long id) {
        resumeRepository.deleteById(id);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getResumeJsonForMerge(long resumeId) {
        var dto = resumeMapper.toDto(resumeRepository.getResume(resumeId));

        dto.getCertifications().sort(
            Comparator.comparing(CertificationDto::getIssueDate).reversed()
        );

        dto.getEducations().sort(
            Comparator.comparing((EducationDto it) -> it.getDegreeType().ordinal()).reversed()
                .thenComparing(EducationDto::getStartDate).reversed()
        );

        dto.getJobs().sort(
            Comparator.comparing(JobDto::getStartDate).reversed()
        );

        dto.getLinks().sort(
            Comparator.comparing(LinkDto::getPlatformName)
        );

        dto.getSkills().sort(
            Comparator.comparing((SkillDto it) -> it.getProficiency().ordinal()).reversed()
        );

        dto.getLanguages().sort(
            Comparator.comparing((LanguageDto it) -> it.getProficiency().ordinal()).reversed()
        );

        return (Map<String, Object>)objectMapper.convertValue(dto, Map.class);
    }
}
