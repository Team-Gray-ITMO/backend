package vk.itmo.teamgray.backend.skill.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.resume.services.ResumeService;
import vk.itmo.teamgray.backend.skill.dto.SkillCreateDto;
import vk.itmo.teamgray.backend.skill.dto.SkillUpdateDto;
import vk.itmo.teamgray.backend.skill.entities.Skill;
import vk.itmo.teamgray.backend.skill.repos.SkillRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class SkillService {
    private final SkillRepository skillRepository;
    private final ResumeService resumeService;

    public Skill findById(Long id) {
        return skillRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public Skill createSkill(SkillCreateDto data) {
        return skillRepository.save(new Skill(
            data,
            resumeService.findById(data.resumeId())
        ));
    }

    public Skill updateSkill(SkillUpdateDto data) {
        return skillRepository.save(new Skill(
            data,
            resumeService.findById(data.resumeId())
        ));
    }

    public void deleteById(Long id) {
        skillRepository.deleteById(id);
    }
}
