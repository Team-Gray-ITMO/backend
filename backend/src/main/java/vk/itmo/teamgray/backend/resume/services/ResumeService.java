package vk.itmo.teamgray.backend.resume.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Comparator;
import java.util.List;
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
import vk.itmo.teamgray.backend.template.repos.TemplateRepository;
import vk.itmo.teamgray.backend.user.repos.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final TemplateRepository templateRepository;

    public List<ResumeDto> findAll() {
        return resumeMapper.toDtoList(resumeRepository.findAllAndFetch());
    }

    private final ResumeMapper resumeMapper;

    private final ObjectMapper objectMapper;

    public Resume findEntityById(Long id) {
        return resumeRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public ResumeDto findById(Long id) {
        return resumeMapper.toDto(resumeRepository.findById(id).orElseThrow(ModelNotFoundException::new));
    }

    public ResumeDto createResume(ResumeCreateDto data) {
        return createResume(data, true);
    }

    public ResumeDto createResume(ResumeCreateDto data, boolean persist) {
        //TODO Resolve user from auth context.
        var user = userRepository.findById(data.userId())
            .orElseThrow(ModelNotFoundException::new);

        var template = persist
            ? templateRepository.findById(data.templateId()).orElseThrow(ModelNotFoundException::new)
            : null;

        var resume = new Resume(data, user, template);

        if (persist) {
            resume = resumeRepository.save(resume);
        }

        return resumeMapper.toDto(resume);
    }

    public ResumeDto updateResume(ResumeUpdateDto data) {
        //TODO Maybe resolve user from auth context.
        var template = templateRepository.findById(data.templateId())
            .orElseThrow(ModelNotFoundException::new);

        return resumeMapper.toDto(
            resumeRepository.save(
                new Resume(
                    data,
                    template
                )
            )
        );
    }

    public void deleteById(Long id) {
        resumeRepository.deleteById(id);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getResumeJsonForMerge(ResumeDto dto) {
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
