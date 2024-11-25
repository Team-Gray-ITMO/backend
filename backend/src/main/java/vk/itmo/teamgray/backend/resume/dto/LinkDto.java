package vk.itmo.teamgray.backend.resume.dto;

import lombok.Data;

@Data
public class LinkDto {
    private long id;
    private String platformName;
    private String profileUrl;
}
