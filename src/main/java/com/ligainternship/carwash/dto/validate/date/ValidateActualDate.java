package com.ligainternship.carwash.dto.validate.date;

import com.ligainternship.carwash.exception.InvalidDateException;
import com.ligainternship.carwash.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@Slf4j
public class ValidateActualDate implements ConstraintValidator<ValidActualDate, String> {

 @Override
    public boolean isValid(String inputDate, ConstraintValidatorContext context) {
        try {
            LocalDate startDate = StringUtils.parseDate(inputDate);
            isDateValid(startDate);
        } catch (Exception exception) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(exception.getMessage())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }


    private boolean isDateValid(LocalDate startDate) {
        LocalDate now = LocalDate.now();
        if (startDate.isBefore(now)) {
            String message = "Booking date should be actual";
            log.error(message);
            throw new InvalidDateException(message);
        }
        return true;
    }
}
