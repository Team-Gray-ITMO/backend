package vk.itmo.teamgray.backend.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "User Model")
public class UserDto {

    @Schema(description = "User's ID")
    private long id;

    @Schema(description = "User's email address")
    private String email;

    @Schema(description = "VK ID")
    private Long vkId;
}
