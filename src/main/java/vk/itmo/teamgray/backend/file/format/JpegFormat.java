package vk.itmo.teamgray.backend.file.format;

public class JpegFormat implements FileFormat {
    public static final String EXTENSION = ".jpg";

    public static final String MIME_TYPE = "image/jpeg";

    @Override
    public String getMimeType() {
        return MIME_TYPE;
    }

    @Override
    public String getExtension() {
        return EXTENSION;
    }
}
