package vk.itmo.teamgray.backend.template.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import vk.itmo.teamgray.backend.template.dto.FileDto;
import vk.itmo.teamgray.backend.template.exception.TemplateMergeServiceException;

public class ZipUtils {
    public static Map<String, byte[]> extractZipContents(FileDto fileDto) {
        Map<String, byte[]> zipContents = new HashMap<>();

        try (
            ByteArrayInputStream bais = new ByteArrayInputStream(fileDto.getContent());
            ZipInputStream zis = new ZipInputStream(bais)
        ) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                byte[] content = zis.readAllBytes();
                zipContents.put(entry.getName(), content);
            }
        } catch (IOException e) {
            throw new TemplateMergeServiceException("Failed to extract ZIP contents.", e);
        }
        return zipContents;
    }

    public static byte[] repackZip(Map<String, byte[]> zipContents) {
        try (
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(baos)
        ) {
            for (Map.Entry<String, byte[]> entry : zipContents.entrySet()) {
                ZipEntry zipEntry = new ZipEntry(entry.getKey());
                zos.putNextEntry(zipEntry);
                zos.write(entry.getValue());
                zos.closeEntry();
            }

            zos.finish();

            return baos.toByteArray();
        } catch (IOException e) {
            throw new TemplateMergeServiceException("Failed to repack ZIP contents.", e);
        }
    }
}
