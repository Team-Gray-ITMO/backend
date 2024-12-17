package vk.itmo.teamgray.backend.file.format;

public class ZipFormat implements FileFormat {
    public static final String EXTENSION = ".zip";

    public static final String MIME_TYPE = "application/x-zip";

    @Override
    public String getMimeType() {
        return MIME_TYPE;
    }

    @Override
    public String getExtension() {
        return EXTENSION;
    }
}
