package vk.itmo.teamgray.backend.file.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "The name of the file")
    private String filename;

    @Getter
    @Setter
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
    public byte[] getContent() {
        return content;
    }

    @JsonIgnore
    public void setContent(byte[] content) {
        this.content = content;
    }
}
