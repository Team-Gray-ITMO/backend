package vk.itmo.teamgray.backend.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
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

    @Schema(description = "Phone number")
    private String phoneNumber;

    @Schema(description = "Date of birth")
    private Date dateOfBirth;

    @Schema(description = "City name")
    private String cityName;
}
