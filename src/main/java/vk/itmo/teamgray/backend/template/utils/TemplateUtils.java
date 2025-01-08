package vk.itmo.teamgray.backend.template.utils;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import vk.itmo.teamgray.backend.common.enums.TranslatableEnum;

public class TemplateUtils {
    public static String formatDateAsMonthOrNow(Date date) {
        if (date == null) {
            return "н.в.";
        }

        return formatDate(date);
    }

    public static String formatDateAsMonth(Date date) {
        if (date == null) {
            return null;
        }

        return formatDate(date);
    }

    private static String formatDate(Date date) {
        return DateTimeFormatter.ofPattern("MMM, yyyy")
            .withZone(ZoneId.systemDefault())
            .format(date.toInstant());
    }

    public static String formatBirthDate(Date date) {
        if (date == null) {
            return null;
        }

        return DateTimeFormatter.ofPattern("dd.MM.yyyy")
            .withZone(ZoneId.systemDefault())
            .format(date.toInstant());
    }

    public String trim(String string, int maxLength) {
        if (string == null || string.length() < maxLength) {
            return string;
        }

        return string.substring(0, maxLength) + "[...]";
    }

    public String translate(TranslatableEnum translatable) {
        return translatable.getTranslatedName();
    }
}
