package vk.itmo.teamgray.backend.education.enums;

import lombok.Getter;
import vk.itmo.teamgray.backend.common.enums.TranslatableEnum;

@Getter
public enum EducationFormat implements TranslatableEnum {
    FULL_TIME("Очная"),
    PART_TIME("Очно-Заочная"),
    CORRESPONDENCE("Заочная");

    private final String translatedName;

    EducationFormat(String translatedName) {
        this.translatedName = translatedName;
    }
}
