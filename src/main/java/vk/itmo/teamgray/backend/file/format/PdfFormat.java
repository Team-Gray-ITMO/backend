package vk.itmo.teamgray.backend.file.format;

public class PdfFormat implements FileFormat {
    public static final String EXTENSION = ".pdf";

    public static final String MIME_TYPE = "application/pdf";

    @Override
    public String getMimeType() {
        return MIME_TYPE;
    }

    @Override
    public String getExtension() {
        return EXTENSION;
    }
}
