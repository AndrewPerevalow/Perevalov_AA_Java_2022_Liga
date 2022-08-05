package com.ligainternship.carwash.util;

import com.ligainternship.carwash.exception.InvalidTimeException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@UtilityClass
@Slf4j
public class StringUtils {

    private static final String TIME_PATTERN = "HH:mm:ss";

    public static LocalTime parseTime(String inputTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        LocalTime time;
        try {
            time = LocalTime.parse(inputTime, formatter);
        } catch (DateTimeParseException exception) {
            String message = "Invalid time input. Pattern should be HH:mm:ss. " + exception.getMessage();
            log.error(message);
            throw new InvalidTimeException(message);
        }
        return time;
    }
}
