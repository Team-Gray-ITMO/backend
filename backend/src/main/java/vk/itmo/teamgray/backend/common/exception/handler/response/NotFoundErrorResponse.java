package vk.itmo.teamgray.backend.common.exception.handler.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Not Found Error Response")
public class NotFoundErrorResponse implements ErrorResponse {
    @JsonIgnore
    public static final String CODE = "404";

    @Getter
    @Setter
    @Schema(description = "Description of the error", example = "Not Found")
    private String message;

    @Schema(description = "HTTP status code of the error", example = CODE)
    @Override
    public String getCode() {
        return CODE;
    }
}
