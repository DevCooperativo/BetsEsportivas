package com.betsesportivas.Helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateConverterHelper {

    public static LocalDate ConvertTimestampsToLocalDate(LocalDateTime value) {
        return value.toLocalDate();
    }

    public static String ConvertTimestampsToFormated(LocalDateTime value) {
        return value.format(DateTimeFormatter.ofPattern("HH-mm"));
    }

    public static LocalDateTime ConvertStringToLocalDateTime(String value) {
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm"));
    }

}
