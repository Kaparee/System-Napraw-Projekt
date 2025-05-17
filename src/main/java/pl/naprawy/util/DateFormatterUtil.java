package pl.naprawy.util;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class DateFormatterUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static String format(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime().format(FORMATTER) : "";
    }
}
