package vk.itmo.teamgray.backend.education.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.education.dto.EducationInstitutionCreateDto;
import vk.itmo.teamgray.backend.education.dto.EducationInstitutionUpdateDto;
import vk.itmo.teamgray.backend.education.entities.EducationInstitution;
import vk.itmo.teamgray.backend.education.repos.EducationInstitutionRepository;

@Service
@RequiredArgsConstructor
public class EducationInstitutionService {
    private final EducationInstitutionRepository educationInstitutionRepository;

    public EducationInstitution findById(Long id) {
        return educationInstitutionRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public EducationInstitution createEducationInstitution(EducationInstitutionCreateDto data) {
        return educationInstitutionRepository.save(new EducationInstitution(
                data
        ));
    }

    public EducationInstitution updateEducationInstitution(EducationInstitutionUpdateDto data) {
        return educationInstitutionRepository.save(new EducationInstitution(
                data
        ));
    }

    public void deleteById(Long id){
        educationInstitutionRepository.deleteById(id);
    }
}
