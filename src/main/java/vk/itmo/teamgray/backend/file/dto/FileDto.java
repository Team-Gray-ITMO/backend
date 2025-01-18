package vk.itmo.teamgray.backend.file.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Base64;


@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "File Model")
public class FileDto {
    @Getter
    @Setter
    @NotBlank
    @Schema(description = "The name of the file")
    private String filename;

    @Getter
    @Setter
    @NotBlank
    @Schema(description = "The content type of the file")
    private String contentType;

    @Schema(description = "The binary content of the file")
    private byte[] content;

    //Serializing as base64, using as byte[]
    public String getContentBase64() {
        return Base64.getEncoder().encodeToString(content);
    }

    public void setContentBase64(String content) {
        this.content = Base64.getDecoder().decode(content);
    }

    @JsonIgnore
    public boolean isContentPresent() {
        return content != null && content.length > 0;
    }

    @JsonIgnore
    public byte[] getContent() {
        return content;
    }

    @JsonIgnore
    public void setContent(byte[] content) {
        this.content = content;
    }
}
