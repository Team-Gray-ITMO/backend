package vk.itmo.teamgray.backend.education.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.education.dto.EducationCreateDto;
import vk.itmo.teamgray.backend.education.dto.EducationUpdateDto;
import vk.itmo.teamgray.backend.education.entities.Education;
import vk.itmo.teamgray.backend.education.repos.EducationRepository;
import vk.itmo.teamgray.backend.resume.services.ResumeService;

@Service
@RequiredArgsConstructor
@Transactional
public class EducationService {
    private final EducationRepository educationRepository;
    private final ResumeService resumeService;
    private final EducationInstitutionService educationInstitutionService;

    public Education findById(Long id) {
        return educationRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public Education createEducation(EducationCreateDto data) {
        return educationRepository.save(new Education(
            data,
            resumeService.findById(data.resumeId()),
            educationInstitutionService.findById(data.educationInstitutionId())
        ));
    }

    public Education updateEducation(EducationUpdateDto data) {
        return educationRepository.save(new Education(
            data,
            resumeService.findById(data.resumeId()),
            educationInstitutionService.findById(data.educationInstitutionId())
        ));
    }

    public void deleteById(Long id) {
        educationRepository.deleteById(id);
    }
}
