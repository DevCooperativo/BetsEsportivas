package com.betsesportivas.Helpers;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateConverterHelper {

    public static LocalDate ConvertTimestampsToLocalDate(LocalDateTime value) {
        return value.toLocalDate();
    }

    public static String ConvertTimestampsToFormated(LocalDateTime value) {
        return value.format(DateTimeFormatter.ofPattern("HH-mm"));
    }

    public static LocalDateTime ConvertStringToLocalDateTime(String value) throws Exception {
        try {
            LocalDateTime returnable = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm"));
            return returnable;
        } catch (Exception e) {
            if(e instanceof DateTimeParseException){
                throw new Exception("A hora está em um formato irregular. Corrija-a para o formato HH-mm");
            }
            throw new Exception("A data ou a hora inserida estão em um formato irregular. Confira se o ano foi inserido como dd/MM/yyyy e a hora como HH-mm");
        }
    }

    public static Timestamp ConvertLocalDateTimeToTimestamp(LocalDateTime value) {
        return Timestamp.from(value.atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
    }

    public static Timestamp ConvertLocalDateToTimestamp(LocalDate value) {
        return Timestamp.from(value.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

}
