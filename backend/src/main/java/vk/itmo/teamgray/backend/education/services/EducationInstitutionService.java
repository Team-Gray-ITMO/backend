package vk.itmo.teamgray.backend.education.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.education.dto.EducationInstitutionCreateDto;
import vk.itmo.teamgray.backend.education.dto.EducationInstitutionDto;
import vk.itmo.teamgray.backend.education.dto.EducationInstitutionUpdateDto;
import vk.itmo.teamgray.backend.education.entities.EducationInstitution;
import vk.itmo.teamgray.backend.education.mapper.EducationInstitutionMapper;
import vk.itmo.teamgray.backend.education.repos.EducationInstitutionRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class EducationInstitutionService {
    private final EducationInstitutionRepository educationInstitutionRepository;

    private final EducationInstitutionMapper educationInstitutionMapper;

    public EducationInstitution findEntityById(Long id) {
        return educationInstitutionRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public EducationInstitutionDto findById(Long id) {
        return educationInstitutionMapper.toDto(findEntityById(id));
    }

    public EducationInstitutionDto createEducationInstitution(EducationInstitutionCreateDto data) {
        return educationInstitutionMapper.toDto(
            educationInstitutionRepository.save(new EducationInstitution(
                data
            ))
        );
    }

    public EducationInstitutionDto updateEducationInstitution(EducationInstitutionUpdateDto data) {
        return educationInstitutionMapper.toDto(
            educationInstitutionRepository.save(new EducationInstitution(
                data
            ))
        );
    }

    public void deleteById(Long id) {
        educationInstitutionRepository.deleteById(id);
    }
}
