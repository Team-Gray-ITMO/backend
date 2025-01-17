package vk.itmo.teamgray.backend.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vk.itmo.teamgray.backend.common.validation.NotBlankOrNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "User Update Model")
public class UserUpdateDto implements Serializable {
    @Schema(description = "User's email address")
    @NotBlankOrNull
    @Email
    private String email;

    @Schema(description = "Phone number")
    private String phoneNumber;

    @Past
    @Schema(description = "Date of birth")
    private Date dateOfBirth;

    @Schema(description = "City name")
    private String cityName;

    @Schema(description = "Preferred Contact")
    private PreferredContact preferredContact;
}
