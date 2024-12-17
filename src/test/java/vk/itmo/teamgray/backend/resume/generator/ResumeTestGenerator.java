package vk.itmo.teamgray.backend.resume.generator;

import java.util.List;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vk.itmo.teamgray.backend.resume.dto.ResumeDto;

@Component
public class ResumeTestGenerator {
    @Autowired
    private ResumeSampleGenerator resumeSampleGenerator;

    public List<ResumeDto> generateResumes(int amount, long templateId) {
        return IntStream.range(1, amount + 1)
            .mapToObj(i -> resumeSampleGenerator.generateResume(templateId, String.valueOf(i), true))
            .toList();
    }
}
