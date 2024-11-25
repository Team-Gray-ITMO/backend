package vk.itmo.teamgray.backend.language.dto;

import lombok.Data;
import vk.itmo.teamgray.backend.language.enums.LanguageProficiency;

@Data
public class LanguageDto {
    private long id;
    private String name;
    private LanguageProficiency proficiency;
}
