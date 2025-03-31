package registroponto.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static String formatarDataHora(LocalDateTime dataHora) {
        return dataHora.format(FORMATTER);
    }
}
