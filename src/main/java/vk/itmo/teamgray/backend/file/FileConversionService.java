package vk.itmo.teamgray.backend.file;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.springframework.stereotype.Service;
import vk.itmo.teamgray.backend.resume.exceptions.ConversionException;

import static vk.itmo.teamgray.backend.file.utils.FileUtils.getLocalResources;

@Service
public class FileConversionService {
    private PageSize pageSize = PageSize.A4;

    private ConverterProperties converterProperties = getConverterProperties(pageSize);

    public byte[] convertPdfToPng(byte[] pdfFile) {
        List<byte[]> pngPages = convertPdfToPngPages(pdfFile, 1);

        if (pngPages.isEmpty()) {
            throw new ConversionException("ERROR.CONVERT_TO_PNG: No pages found in PDF.");
        }

        return pngPages.getFirst();
    }

    public byte[] convertHtmlToPdf(byte[] htmlTemplate) {
        try (ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream()) {
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(pdfOutputStream));

            pdfDocument.setDefaultPageSize(pageSize);

            HtmlConverter.convertToPdf(new ByteArrayInputStream(htmlTemplate), pdfDocument, converterProperties);

            pdfDocument.close();

            return pdfOutputStream.toByteArray();
        } catch (IOException e) {
            throw new ConversionException("ERROR.CONVERT_TO_PDF: " + e.getMessage());
        }
    }

    public byte[] convertHtmlToDocx(byte[] htmlTemplate) {
        String htmlContent = new String(htmlTemplate, StandardCharsets.UTF_8);
        Document htmlDocument = Jsoup.parse(htmlContent);

        boolean hasStyles = !htmlDocument.select("style").isEmpty() || !htmlDocument.select("[style]").isEmpty();

        try (XWPFDocument docxDocument = createDocumentWithMinimalMargins(hasStyles)) {
            if (hasStyles) {
                return convertStyledHtmlToDocx(htmlTemplate, docxDocument);
            } else {
                return convertPlainHtmlToDocx(htmlDocument, docxDocument);
            }
        } catch (IOException | InvalidFormatException e) {
            throw new ConversionException("ERROR.CONVERT_TO_DOCX: " + e.getMessage());
        }
    }

    private XWPFDocument createDocumentWithMinimalMargins(boolean hasStyles) {
        XWPFDocument document = new XWPFDocument();
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();

        int marginTwips = hasStyles
            ? 0
            : 567; //10mm

        pageMar.setTop(marginTwips);
        pageMar.setBottom(marginTwips);
        pageMar.setLeft(marginTwips);
        pageMar.setRight(marginTwips);

        return document;
    }

    private byte[] convertStyledHtmlToDocx(byte[] htmlTemplate, XWPFDocument docxDocument) throws IOException, InvalidFormatException {
        List<byte[]> pngPages = convertPdfToPngPages(convertHtmlToPdf(htmlTemplate), null);

        for (byte[] imageBytes : pngPages) {
            XWPFParagraph paragraph = docxDocument.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.addPicture(
                new ByteArrayInputStream(imageBytes),
                XWPFDocument.PICTURE_TYPE_PNG,
                "Page.png",
                Units.toEMU(pageSize.getWidth()),
                Units.toEMU(pageSize.getHeight())
            );
        }

        return writeDocxToByteArray(docxDocument);
    }

    private byte[] convertPlainHtmlToDocx(Document htmlDocument, XWPFDocument docxDocument) throws IOException {
        Elements paragraphs = htmlDocument.body().select("p, h1, h2, h3, h4, h5, h6");

        for (Element paragraph : paragraphs) {
            XWPFParagraph docParagraph = docxDocument.createParagraph();
            XWPFRun run = docParagraph.createRun();
            run.setText(paragraph.text());

            switch (paragraph.tagName()) {
                case "h1" -> run.setBold(true);
                case "h2" -> {
                    run.setBold(true);
                    run.setFontSize(18);
                }
                case "h3" -> run.setFontSize(16);
            }
        }

        return writeDocxToByteArray(docxDocument);
    }

    private byte[] writeDocxToByteArray(XWPFDocument document) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            document.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    private List<byte[]> convertPdfToPngPages(byte[] pdfFile, Integer limit) {
        List<byte[]> pngImages = new ArrayList<>();

        try (PDDocument document = new PDFParser(new RandomAccessReadBuffer(pdfFile)).parse()) {
            PDFRenderer renderer = new PDFRenderer(document);

            limit = Math.min(Objects.requireNonNullElse(limit, document.getNumberOfPages()), document.getNumberOfPages());

            for (int pageIndex = 0; pageIndex < limit; pageIndex++) {
                BufferedImage image = renderer.renderImageWithDPI(pageIndex, 300, ImageType.RGB);

                ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "PNG", pngOutputStream);

                pngImages.add(pngOutputStream.toByteArray());
            }
        } catch (IOException e) {
            throw new ConversionException("ERROR.CONVERT_PDF_TO_PNG: " + e.getMessage());
        }

        return pngImages;
    }

    private static ConverterProperties getConverterProperties(PageSize pageSize) {
        ConverterProperties converterProperties = new ConverterProperties();

        FontProvider fontProvider  = new FontProvider();

        var fonts = getLocalResources("classpath*:/assets/font/*");

        fonts.forEach(resource -> {
            try {
                fontProvider.addFont(resource.getContentAsByteArray());
            } catch (IOException e) {
                throw new ConversionException("ERROR.IMPORT_FONT: " + e.getMessage());
            }
        });

        fontProvider.addStandardPdfFonts();
        fontProvider.addSystemFonts();
        converterProperties.setFontProvider(fontProvider);

        MediaDeviceDescription mediaDeviceDescription = new MediaDeviceDescription(MediaType.SCREEN);
        mediaDeviceDescription.setWidth(pageSize.getWidth());
        converterProperties.setMediaDeviceDescription(mediaDeviceDescription);
        return converterProperties;
    }
}
