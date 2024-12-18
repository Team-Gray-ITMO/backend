package vk.itmo.teamgray.backend.file.format;

public class VmFormat implements FileFormat {
    public static final String EXTENSION = ".vm";

    public static final String MIME_TYPE = "text/velocity";

    @Override
    public String getMimeType() {
        return MIME_TYPE;
    }

    @Override
    public String getExtension() {
        return EXTENSION;
    }
}
