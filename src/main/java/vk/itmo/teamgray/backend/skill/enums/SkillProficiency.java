package vk.itmo.teamgray.backend.skill.enums;

import lombok.Getter;
import vk.itmo.teamgray.backend.common.enums.TranslatableEnum;

@Getter
public enum SkillProficiency implements TranslatableEnum {
    BEGINNER("Начальный"),
    INTERMEDIATE("Средний"),
    ADVANCED("Продвинутый"),
    EXPERT("Экспертный");

    private final String translatedName;

    SkillProficiency(String translatedName) {
        this.translatedName = translatedName;
    }
}
