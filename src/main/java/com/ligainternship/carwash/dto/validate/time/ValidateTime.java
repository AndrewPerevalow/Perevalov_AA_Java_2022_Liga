package com.ligainternship.carwash.dto.validate.time;

import com.ligainternship.carwash.exception.InvalidTimeException;
import com.ligainternship.carwash.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;

@Slf4j
public class ValidateTime implements ConstraintValidator<ValidTime, String> {

    @Override
    public boolean isValid(String inputTime, ConstraintValidatorContext context) {
        try {
            LocalTime time = StringUtils.parseTime(inputTime);
            isTimeValid(time);
        } catch (Exception exception) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(exception.getMessage())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean isTimeValid(LocalTime startTime) {
        LocalTime now = LocalTime.now();
        if (startTime.isBefore(now)) {
            String message = "Booking time should be actual";
            log.error(message);
            throw new InvalidTimeException(message);
        }
        return true;
    }
}
