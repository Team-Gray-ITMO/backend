package vk.itmo.teamgray.backend.file.format;

public class HtmlFormat implements FileFormat {
    public static final String EXTENSION = ".html";

    public static final String MIME_TYPE = "application/octet-stream";

    @Override
    public String getMimeType() {
        return MIME_TYPE;
    }

    @Override
    public String getExtension() {
        return EXTENSION;
    }
}
