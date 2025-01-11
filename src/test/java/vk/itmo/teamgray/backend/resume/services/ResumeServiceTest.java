package vk.itmo.teamgray.backend.resume.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vk.itmo.teamgray.backend.TestBase;

class ResumeServiceTest extends TestBase {
    private static final Logger log = LoggerFactory.getLogger(ResumeServiceTest.class);

    @Autowired
    private ResumeService resumeService;

    @Test
    void testJsonAggregation() throws JsonProcessingException {
        var resumes = resumeGenerator.generateResumes(5, sampleTemplates.getFirst().getId()).stream()
            .map(resume -> resumeService.prepareResume(resume))
            .toList();

        //TODO Replace with proper assertions
        log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resumes));
    }
}
