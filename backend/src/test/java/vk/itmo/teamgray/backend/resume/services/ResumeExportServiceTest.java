package vk.itmo.teamgray.backend.resume.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vk.itmo.teamgray.backend.TestBase;
import vk.itmo.teamgray.backend.resume.dto.ResumeDto;

import java.nio.charset.StandardCharsets;

class ResumeExportServiceTest extends TestBase {
    private static final Logger log = LoggerFactory.getLogger(ResumeExportServiceTest.class);

    @Autowired
    private ResumeExportService resumeExportService;

    @Test
    void testJsonAggregation() throws JsonProcessingException {
        var resume = resumeGenerator.generateResumes(1, sampleTemplate.getId()).getFirst();

        byte[] exportResult = resumeExportService.extractHtml(resume.getId());
        String exportAsString = new String(exportResult, StandardCharsets.UTF_8);

        log.info(exportAsString);
        // TODO Add assertions
    }
}
