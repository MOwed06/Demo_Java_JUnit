package demo.mowed.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeHelper {
    public static String formatDate(LocalDateTime value) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
        return value.format(fmt);
    }

    public static String formatDateTime(LocalDateTime value) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
        return value.format(fmt);
    }

    /*
    convert date string to LocalDateTime
    if no HH:mm:ss present, assume hour min sec component
     */
    public static LocalDateTime parse(String value) {
        // check regex for pattern
        if (value.matches("^\\d{2}:\\d{2}:\\d{2}$")) {
            // HH:mm:ss pattern found, assume digits valid
            return LocalDateTime.parse(value);
        }
        // no HH:mm:ss pattern found, assume midnight
        LocalDate dateValue = LocalDate.parse(value);
        return dateValue.atStartOfDay();
    }
}
