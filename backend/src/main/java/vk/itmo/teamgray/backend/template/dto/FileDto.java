package vk.itmo.teamgray.backend.template.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    private String filename;

    private String contentType;

    //TODO Maybe use InputStreams?
    private byte[] content;
}
