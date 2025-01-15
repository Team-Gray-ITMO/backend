package vk.itmo.teamgray.backend.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "User Create Model")
public class UserCreateDto implements Serializable {
    @Schema(description = "User's email address")
    @NotNull
    @NotEmpty
    private String email;

    @Schema(description = "VK ID")
    @NotNull
    @NotEmpty
    private Long vkId;

    @Schema(description = "Phone number")
    private String phoneNumber;

    @Schema(description = "Date of birth")
    private Date dateOfBirth;

    @Schema(description = "City name")
    private String cityName;

    @Schema(description = "Preferred Contact")
    private PreferredContact preferredContact;
}
