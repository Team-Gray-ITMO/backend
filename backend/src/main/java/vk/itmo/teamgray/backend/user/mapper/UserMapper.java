package vk.itmo.teamgray.backend.user.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vk.itmo.teamgray.backend.user.dto.UserDto;
import vk.itmo.teamgray.backend.user.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User entity);

    List<UserDto> toDtoList(List<User> entities);
}
