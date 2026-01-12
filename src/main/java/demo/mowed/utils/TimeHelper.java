package demo.mowed.utils;

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
}
