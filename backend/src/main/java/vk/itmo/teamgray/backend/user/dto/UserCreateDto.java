package vk.itmo.teamgray.backend.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "User Create Model")
public class UserCreateDto implements Serializable {
    @Schema(description = "User's email address")
    private String email;

    @Schema(description = "VK ID")
    private Long vkId;
}
