package vk.itmo.teamgray.backend.skill.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.common.service.BaseService;
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
public class SkillService extends BaseService<Skill> {
    private final SkillRepository skillRepository;
    private final ResumeService resumeService;
    private final SkillMapper skillMapper;

    @Override
    public Skill findEntityById(Long id) {
        return skillRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public SkillDto findById(Long id) {
        return skillMapper.toDto(findEntityById(id));
    }

    public SkillDto createSkill(SkillCreateDto data) {
        return createSkill(data, true);
    }

    public SkillDto createSkill(SkillCreateDto data, boolean persist) {
        var resume = resumeService.findEntityById(data.getResumeId());

        var skill = new Skill(data, resume);

        if (persist) {
            skill = skillRepository.save(skill);
        }

        return skillMapper.toDto(skill);
    }

    public SkillDto updateSkill(SkillUpdateDto updateDto) {
        var skill = findEntityById(updateDto.getId());

        boolean updated = false;

        updated |= updateIfPresent(updateDto.getName(), skill::setName);
        updated |= updateIfPresent(updateDto.getProficiency(), skill::setProficiency);

        updated |= resumeService.updateLinkToEntityIfPresent(updateDto.getResumeId(), skill::setResume);

        if (updated) {
            skill = skillRepository.save(skill);
        }

        return skillMapper.toDto(skill);
    }

    public void deleteById(Long id) {
        skillRepository.deleteById(id);
    }
}
