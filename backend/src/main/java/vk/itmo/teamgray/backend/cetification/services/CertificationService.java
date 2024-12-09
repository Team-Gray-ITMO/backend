package vk.itmo.teamgray.backend.cetification.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.cetification.dto.CertificationCreateDto;
import vk.itmo.teamgray.backend.cetification.dto.CertificationDto;
import vk.itmo.teamgray.backend.cetification.dto.CertificationUpdateDto;
import vk.itmo.teamgray.backend.cetification.entities.Certification;
import vk.itmo.teamgray.backend.cetification.mapper.CertificationMapper;
import vk.itmo.teamgray.backend.cetification.repos.CertificationRepository;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.resume.services.ResumeService;

@Service
@RequiredArgsConstructor
@Transactional
public class CertificationService {
    private final CertificationRepository certificationRepository;
    private final ResumeService resumeService;
    private final CertificationMapper certificationMapper;

    public Certification findEntityById(Long id) {
        return certificationRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public CertificationDto findById(Long id) {
        return certificationMapper.toDto(findEntityById(id));
    }

    public CertificationDto createCertification(CertificationCreateDto data) {
        return createCertification(data, true);
    }

    public CertificationDto createCertification(CertificationCreateDto data, boolean persist) {
        var resume = resumeService.findEntityById(data.resumeId());
        var certification = new Certification(data, resume);

        if (persist) {
            certification = certificationRepository.save(certification);
        }

        return certificationMapper.toDto(certification);
    }

    public CertificationDto updateCertification(CertificationUpdateDto data) {
        return certificationMapper.toDto(
            certificationRepository.save(new Certification(
                data,
                resumeService.findEntityById(data.resumeId())
            ))
        );
    }

    public void deleteById(Long id) {
        certificationRepository.deleteById(id);
    }
}
