package vk.itmo.teamgray.backend.template.services;

import org.junit.jupiter.api.Test;
import vk.itmo.teamgray.backend.TestBase;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.template.dto.FileDto;
import vk.itmo.teamgray.backend.template.dto.TemplateCreateDto;
import vk.itmo.teamgray.backend.template.dto.TemplateUpdateDto;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TemplateServiceTest extends TestBase {
    public static final byte[] EMPTY_BYTE_ARRAY = {};

    @Test
    void testTemplate() {
        var fileDto = sampleTemplate.getFile();
        var name = sampleTemplate.getName() + "2";

        var template = templateService.createTemplate(
            new TemplateCreateDto(
                name,
                fileDto
            )
        );

        template = templateService.findById(template.getId());

        assertEquals(name, template.getName());
        assertArrayEquals(fileDto.getContent(), template.getFile().getContent());

        name += "2";

        var fileDto2 = new FileDto();

        fileDto2.setFilename(fileDto.getFilename());
        fileDto2.setContentType("application/zip");
        fileDto2.setContent(EMPTY_BYTE_ARRAY);

        templateService.updateTemplate(
            new TemplateUpdateDto(
                template.getId(),
                name,
                fileDto2
            )
        );

        template = templateService.findById(template.getId());

        assertEquals(name, template.getName());
        assertArrayEquals(fileDto2.getContent(), template.getFile().getContent());

        templateService.deleteById(template.getId());

        var templateId = template.getId();

        assertThrows(ModelNotFoundException.class, () -> templateService.findById(templateId));
    }
}
