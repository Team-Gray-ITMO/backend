package vk.itmo.teamgray.backend.resume.services;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.resume.dto.ResumeCreateDto;
import vk.itmo.teamgray.backend.resume.dto.ResumeUpdateDto;
import vk.itmo.teamgray.backend.resume.entities.Resume;
import vk.itmo.teamgray.backend.resume.repos.ResumeRepository;
import vk.itmo.teamgray.backend.user.repos.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeService {
    private final ResumeRepository resumeRepository;

    private final UserRepository userRepository;

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

    public Map<String, Object> getResumeJson(long resumeId) {
        return resumeRepository.getResumeJson(resumeId);
    }
}
