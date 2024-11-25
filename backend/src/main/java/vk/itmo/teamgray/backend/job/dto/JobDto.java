package vk.itmo.teamgray.backend.job.dto;

import java.util.Date;
import lombok.Data;

@Data
public class JobDto {
    private long id;
    private String title;
    private String company;
    private String location;
    private Date startDate;
    private Date endDate;
    private String description;
}
