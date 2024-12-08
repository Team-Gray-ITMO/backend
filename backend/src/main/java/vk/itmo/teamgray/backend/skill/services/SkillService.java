package vk.itmo.teamgray.backend.skill.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.resume.services.ResumeService;
import vk.itmo.teamgray.backend.skill.dto.SkillCreateDto;
import vk.itmo.teamgray.backend.skill.dto.SkillDto;
import vk.itmo.teamgray.backend.skill.dto.SkillUpdateDto;
import vk.itmo.teamgray.backend.skill.entities.Skill;
import vk.itmo.teamgray.backend.skill.mapper.SkillMapper;
import vk.itmo.teamgray.backend.skill.repos.SkillRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class SkillService {
    private final SkillRepository skillRepository;
    private final ResumeService resumeService;
    private final SkillMapper skillMapper;

    public Skill findEntityById(Long id) {
        return skillRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public SkillDto findById(Long id) {
        return skillMapper.toDto(findEntityById(id));
    }

    public SkillDto createSkill(SkillCreateDto data) {
        return skillMapper.toDto(
            skillRepository.save(new Skill(
                data,
                resumeService.findEntityById(data.resumeId())
            ))
        );
    }

    public SkillDto updateSkill(SkillUpdateDto data) {
        return skillMapper.toDto(
            skillRepository.save(new Skill(
                data,
                resumeService.findEntityById(data.resumeId())
            ))
        );
    }

    public void deleteById(Long id) {
        skillRepository.deleteById(id);
    }
}
