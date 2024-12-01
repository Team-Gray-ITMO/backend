package vk.itmo.teamgray.backend.resume.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vk.itmo.teamgray.backend.TestBase;
import vk.itmo.teamgray.backend.resume.dto.ResumeDto;

class ResumeServiceTest extends TestBase {
    private static final Logger log = LoggerFactory.getLogger(ResumeServiceTest.class);

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testJsonAggregation() throws JsonProcessingException {
        var resumeIds = resumeGenerator.generateResumes(5).stream()
            .map(ResumeDto::getId)
            .toList();

        var resumes = resumeIds.stream()
            .map(id -> resumeService.getResumeJsonForMerge(id))
            .toList();

        //TODO Replace with proper assertions
        log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resumes));
    }
}
