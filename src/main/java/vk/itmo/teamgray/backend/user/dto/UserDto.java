package vk.itmo.teamgray.backend.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Schema(description = "User Model")
public class UserDto extends UserBaseDto {
    @Schema(description = "User's email address")
    private String email;

    @Schema(description = "Phone number")
    private String phoneNumber;

    @Schema(description = "Date of birth")
    private Date dateOfBirth;

    @Schema(description = "City name")
    private String cityName;
}
