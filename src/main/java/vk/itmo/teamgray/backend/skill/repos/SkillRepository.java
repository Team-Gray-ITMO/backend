package vk.itmo.teamgray.backend.skill.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.itmo.teamgray.backend.skill.entities.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}