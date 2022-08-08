package com.ligainternship.carwash.util;

import com.ligainternship.carwash.exception.InvalidDateException;
import com.ligainternship.carwash.exception.InvalidTimeException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@UtilityClass
@Slf4j
public class StringUtils {

    private static final String TIME_PATTERN = "HH:mm";
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    public static LocalTime parseTime(String inputTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        LocalTime time;
        try {
            time = LocalTime.parse(inputTime, formatter);
        } catch (DateTimeParseException exception) {
            String message = "Invalid time input. Pattern should be HH:mm. " + exception.getMessage();
            log.error(message);
            throw new InvalidTimeException(message);
        }
        return time;
    }

    public static LocalDate parseDate(String inputDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDate date;
        try {
            date = LocalDate.parse(inputDate, formatter);
        } catch (DateTimeParseException exception) {
            String message = "Invalid date input. Pattern should be yyyy-MM-dd. " + exception.getMessage();
            log.error(message);
            throw new InvalidDateException(message);
        }
        return date;
    }
}
