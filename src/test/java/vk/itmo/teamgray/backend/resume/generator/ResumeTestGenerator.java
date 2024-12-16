package vk.itmo.teamgray.backend.resume.generator;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vk.itmo.teamgray.backend.resume.dto.ResumeDto;
import vk.itmo.teamgray.backend.user.dto.UserCreateDto;
import vk.itmo.teamgray.backend.user.service.UserService;

@Component
public class ResumeTestGenerator {
    @Autowired
    private ResumeSampleGenerator resumeSampleGenerator;

    @Autowired
    private UserService userService;

    public List<ResumeDto> generateResumes(int amount, long templateId) {
        return IntStream.range(1, amount + 1)
            .mapToObj(i -> {
                var user = userService.createUser(
                    new UserCreateDto(
                        "email" + i + "@example.com",
                        (long)i,
                        String.valueOf(i),
                        Date.from(Instant.now()),
                        "City" + i
                    )
                );

                return resumeSampleGenerator.generateResume(templateId, user.getId(), String.valueOf(i), true);
            })
            .toList();
    }
}
