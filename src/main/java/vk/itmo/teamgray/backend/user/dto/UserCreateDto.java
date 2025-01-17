package vk.itmo.teamgray.backend.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
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
    @NotBlank
    @Email
    private String email;

    @Schema(description = "VK ID")
    @NotNull
    @Positive
    private Long vkId;

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
