package vk.itmo.teamgray.backend.file.format;

public class PngFormat implements FileFormat {
    public static final String EXTENSION = ".png";

    public static final String MIME_TYPE = "image/png";

    @Override
    public String getMimeType() {
        return MIME_TYPE;
    }

    @Override
    public String getExtension() {
        return EXTENSION;
    }
}
