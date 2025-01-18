package vk.itmo.teamgray.backend.job.enums;

import lombok.Getter;
import vk.itmo.teamgray.backend.common.enums.TranslatableEnum;

@Getter
public enum JobAttendanceFormat implements TranslatableEnum {
    ON_SITE("В Офисе"),
    HYBRID("Гибридный"),
    REMOTE("Удалённый");

    private final String translatedName;

    JobAttendanceFormat(String translatedName) {
        this.translatedName = translatedName;
    }
}
