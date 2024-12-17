package vk.itmo.teamgray.backend.file.format;

public class DocxFormat implements FileFormat {
    public static final String EXTENSION = ".docx";

    public static final String MIME_TYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    @Override
    public String getMimeType() {
        return MIME_TYPE;
    }

    @Override
    public String getExtension() {
        return EXTENSION;
    }
}
