package vk.itmo.teamgray.backend.template.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vk.itmo.teamgray.backend.TestBase;
import vk.itmo.teamgray.backend.template.dto.TemplateCreateDto;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TemplateImageServiceTest extends TestBase {
    private static final Logger log = LoggerFactory.getLogger(TemplateImageServiceTest.class);

    @Autowired
    private TemplateImageService templateImageService;

    @Test
    void testTemplate() throws JsonProcessingException {
        var fileDto = sampleTemplate.getFile();
        var name = sampleTemplate.getName() + "2";

        var template = templateService.createTemplate(
            new TemplateCreateDto(
                name,
                fileDto
            )
        );

        var templateWithImage = templateImageService.generateImageForTemplate(template.getId());

        assertNotNull(templateWithImage.getImage().getContent());

        log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(templateWithImage));
    }
}
