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
import javax.imageio.ImageIO;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import vk.itmo.teamgray.backend.resume.exceptions.ConversionException;

import static vk.itmo.teamgray.backend.file.utils.FileUtils.getLocalResources;

@Service
public class FileConversionService {
    private PageSize pageSize = PageSize.A4;

    private ConverterProperties converterProperties = getConverterProperties(pageSize);

    public byte[] convertPdfToPng(byte[] pdfFile) {
        try (PDDocument document = new PDFParser(new RandomAccessReadBuffer(pdfFile)).parse()) {
            PDFRenderer renderer = new PDFRenderer(document);

            BufferedImage image = renderer.renderImageWithDPI(0, 300, ImageType.RGB);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", pngOutputStream);

            return pngOutputStream.toByteArray();

        } catch (IOException e) {
            throw new ConversionException("ERROR.CONVERT_TO_PNG: " + e.getMessage());
        }
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

        XWPFDocument document = new XWPFDocument();

        Document htmlDocument = Jsoup.parse(htmlContent);
        Elements paragraphs = htmlDocument.body().select("p, h1, h2, h3, h4, h5, h6");

        for (Element paragraph : paragraphs) {
            XWPFParagraph docParagraph = document.createParagraph();
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

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            document.write(outputStream);
            document.close();
            return outputStream.toByteArray();
        } catch (IOException ex) {
            throw new ConversionException("ERROR.CONVERT_TO_DOCX: " + ex.getMessage());
        }
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
