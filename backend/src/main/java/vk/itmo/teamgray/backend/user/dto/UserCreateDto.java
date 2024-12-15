package vk.itmo.teamgray.backend.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Date;
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

    @Schema(description = "Phone number")
    private String phoneNumber;

    @Schema(description = "Date of birth")
    private Date dateOfBirth;

    @Schema(description = "City name")
    private String cityName;
}
