package vk.itmo.teamgray.backend.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Schema(description = "User Base Model")
public class UserBaseDto {
    @Schema(description = "User's ID")
    private long id;

    @Schema(description = "VK ID")
    private Long vkId;
}
