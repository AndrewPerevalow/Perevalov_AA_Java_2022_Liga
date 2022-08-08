package com.ligainternship.carwash.dto.validate.date;

import com.ligainternship.carwash.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class ValidateDate implements ConstraintValidator<ValidDate, String> {

 @Override
    public boolean isValid(String inputDate, ConstraintValidatorContext context) {
        try {
            StringUtils.parseDate(inputDate);
        } catch (Exception exception) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(exception.getMessage())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
