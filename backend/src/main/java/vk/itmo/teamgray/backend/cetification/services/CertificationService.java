package vk.itmo.teamgray.backend.cetification.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.cetification.dto.CertificationCreateDto;
import vk.itmo.teamgray.backend.cetification.dto.CertificationUpdateDto;
import vk.itmo.teamgray.backend.cetification.entities.Certification;
import vk.itmo.teamgray.backend.cetification.repos.CertificationRepository;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.resume.services.ResumeService;

@Service
@RequiredArgsConstructor
@Transactional
public class CertificationService {
    private final CertificationRepository certificationRepository;
    private final ResumeService resumeService;

    public Certification findById(Long id) {
        return certificationRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public Certification createCertification(CertificationCreateDto data) {
        return certificationRepository.save(new Certification(
            data,
            resumeService.findById(data.resumeId())
        ));
    }

    public Certification updateCertification(CertificationUpdateDto data) {
        return certificationRepository.save(new Certification(
            data,
            resumeService.findById(data.resumeId())
        ));
    }

    public void deleteById(Long id) {
        certificationRepository.deleteById(id);
    }
}
