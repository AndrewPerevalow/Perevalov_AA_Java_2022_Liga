package com.ligainternship.carwash.dto.validate.discount;

import com.ligainternship.carwash.exception.InvalidDiscountException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class ValidateDiscountName implements ConstraintValidator<ValidDiscountName, String> {

    private static final String MIN_DISCOUNT = "min";
    private static final String MAX_DISCOUNT = "max";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            isDiscountNameValid(value);
        } catch (Exception exception) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(exception.getMessage())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private static boolean isDiscountNameValid(String name) {
        if (!(name.equals(MAX_DISCOUNT) || name.equals((MIN_DISCOUNT)))) {
            String message = String.format("Discount name should be: %s or %s",MIN_DISCOUNT,MAX_DISCOUNT);
            log.error(message);
            throw new InvalidDiscountException(message);
        }
        return true;
    }
}
