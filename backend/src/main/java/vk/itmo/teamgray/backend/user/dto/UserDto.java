package vk.itmo.teamgray.backend.user.dto;

import lombok.Data;

@Data
public class UserDto {
    private long id;
    private String email;
    private Long vkId;
}
