package vk.itmo.teamgray.backend.education.enums;

import lombok.Getter;
import vk.itmo.teamgray.backend.common.enums.TranslatableEnum;

@Getter
public enum EducationDegreeType implements TranslatableEnum {
    UNIVERSITY("Высшее"),
    COLLEGE("Среднее Специальное"),
    HIGH_SCHOOL("Среднее"),
    ELEMENTARY_SCHOOL("Начальное"),
    OTHER("Другое");

    private final String translatedName;

    EducationDegreeType(String translatedName) {
        this.translatedName = translatedName;
    }
}
