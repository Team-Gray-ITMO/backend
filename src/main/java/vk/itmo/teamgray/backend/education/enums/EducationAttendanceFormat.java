package vk.itmo.teamgray.backend.education.enums;

import lombok.Getter;
import vk.itmo.teamgray.backend.common.enums.TranslatableEnum;

@Getter
public enum EducationAttendanceFormat implements TranslatableEnum {
    ON_SITE("Очное Посещение"),
    HYBRID("Гибридный"),
    REMOTE("Удалённый");

    private final String translatedName;

    EducationAttendanceFormat(String translatedName) {
        this.translatedName = translatedName;
    }
}
