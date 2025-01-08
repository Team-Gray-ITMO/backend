package vk.itmo.teamgray.backend.education.enums;

import lombok.Getter;
import vk.itmo.teamgray.backend.common.enums.TranslatableEnum;

@Getter
public enum EducationFormat implements TranslatableEnum {
    FULL_TIME("Очный"),
    PART_TIME("Очно-Заочный"),
    CORRESPONDENCE("Заочный");

    private final String translatedName;

    EducationFormat(String translatedName) {
        this.translatedName = translatedName;
    }
}
