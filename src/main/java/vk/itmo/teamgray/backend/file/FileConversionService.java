package vk.itmo.teamgray.backend.file;

import com.itextpdf.html2pdf.HtmlConverter;
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

@Service
public class FileConversionService {
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
        try {
            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
            HtmlConverter.convertToPdf(new ByteArrayInputStream(htmlTemplate), pdfOutputStream);
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
}
