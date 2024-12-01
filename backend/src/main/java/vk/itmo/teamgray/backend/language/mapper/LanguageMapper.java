package vk.itmo.teamgray.backend.language.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vk.itmo.teamgray.backend.language.dto.LanguageDto;
import vk.itmo.teamgray.backend.language.entities.Language;

@Mapper(componentModel = "spring")
public interface LanguageMapper {
    LanguageMapper INSTANCE = Mappers.getMapper(LanguageMapper.class);

    LanguageDto toDto(Language entity);

    List<LanguageDto> toDtoList(List<Language> entities);
}