package vk.itmo.teamgray.backend.resume.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.resume.dto.ResumeCreateDto;
import vk.itmo.teamgray.backend.resume.dto.ResumeUpdateDto;
import vk.itmo.teamgray.backend.resume.entities.Resume;
import vk.itmo.teamgray.backend.resume.repos.ResumeRepository;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;

    public Resume findById(Long id) {
        return resumeRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public Resume createResume(ResumeCreateDto data) {
        return resumeRepository.save(new Resume(
                data
        ));
    }

    public Resume updateResume(ResumeUpdateDto data) {
        return resumeRepository.save(new Resume(
                data
        ));
    }

    public void deleteById(Long id){
        resumeRepository.deleteById(id);
    }
}
