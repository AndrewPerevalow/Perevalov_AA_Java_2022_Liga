package com.ligainternship.carwash.dto.validate.time;

import com.ligainternship.carwash.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidateTime implements ConstraintValidator<ValidTime, String> {

    @Override
    public boolean isValid(String inputTime, ConstraintValidatorContext context) {
        try {
            StringUtils.parseTime(inputTime);
        } catch (Exception exception) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(exception.getMessage())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
