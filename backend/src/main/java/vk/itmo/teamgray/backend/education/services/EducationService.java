package vk.itmo.teamgray.backend.education.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.education.dto.EducationCreateDto;
import vk.itmo.teamgray.backend.education.dto.EducationDto;
import vk.itmo.teamgray.backend.education.dto.EducationUpdateDto;
import vk.itmo.teamgray.backend.education.entities.Education;
import vk.itmo.teamgray.backend.education.institution.services.EducationInstitutionService;
import vk.itmo.teamgray.backend.education.mapper.EducationMapper;
import vk.itmo.teamgray.backend.education.repos.EducationRepository;
import vk.itmo.teamgray.backend.resume.services.ResumeService;

@Service
@RequiredArgsConstructor
@Transactional
public class EducationService {
    private final EducationRepository educationRepository;
    private final ResumeService resumeService;
    private final EducationInstitutionService educationInstitutionService;
    private final EducationMapper educationMapper;

    public Education findEntityById(Long id) {
        return educationRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public EducationDto findById(Long id) {
        return educationMapper.toDto(findEntityById(id));
    }

    public EducationDto createEducation(EducationCreateDto data) {
        return createEducation(data, true);
    }

    public EducationDto createEducation(EducationCreateDto data, boolean persist) {
        var education = new Education(
            data,
            resumeService.findEntityById(data.getResumeId()),
            educationInstitutionService.findEntityById(data.getEducationInstitutionId())
        );

        if (persist) {
            education = educationRepository.save(education);
        }

        return educationMapper.toDto(education);
    }

    public EducationDto updateEducation(EducationUpdateDto data) {
        return educationMapper.toDto(
            educationRepository.save(new Education(
                data,
                resumeService.findEntityById(data.getResumeId()),
                educationInstitutionService.findEntityById(data.getEducationInstitutionId())
            ))
        );
    }

    public void deleteById(Long id) {
        educationRepository.deleteById(id);
    }
}
