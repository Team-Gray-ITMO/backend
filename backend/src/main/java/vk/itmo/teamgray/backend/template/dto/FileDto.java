package vk.itmo.teamgray.backend.template.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itextpdf.commons.utils.Base64;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
        return Base64.encodeBytes(content);
    }

    public void setContentBase64(String content) {
        this.content = Base64.decode(content);
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
