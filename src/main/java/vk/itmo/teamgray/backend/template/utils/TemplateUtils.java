package vk.itmo.teamgray.backend.template.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.springframework.util.StringUtils.hasText;

public class TemplateUtils {
    public static String formatDate(String date) {
        if (!hasText(date)) {
            return date;
        }

        return DateTimeFormatter.ofPattern("MMM, yyyy")
            .withZone(ZoneId.systemDefault())
            .format(Instant.parse(date));
    }

    public String trim(String string, int maxLength) {
        if (string == null || string.length() < maxLength) {
            return string;
        }

        return string.substring(0, maxLength) + "[...]";
    }
}
