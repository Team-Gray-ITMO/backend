package vk.itmo.teamgray.backend.user.dto;

import java.io.Serializable;

/**
 * DTO for {@link vk.itmo.teamgray.backend.user.entities.User}
 */
public record UserCreateDto(
    String email,
    Long vkId
) implements Serializable {
}
