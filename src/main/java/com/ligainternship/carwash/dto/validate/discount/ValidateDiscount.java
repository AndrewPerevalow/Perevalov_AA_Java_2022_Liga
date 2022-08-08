package com.ligainternship.carwash.dto.validate.discount;

import com.ligainternship.carwash.exception.InvalidDiscountException;
import com.ligainternship.carwash.service.DiscountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
@Slf4j
public class ValidateDiscount implements ConstraintValidator<ValidDiscount, Double> {

    private static final String MIN_DISCOUNT = "min";
    private static final String MAX_DISCOUNT = "max";

    private final DiscountService discountService;

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        try {
            isDiscountValid(value);
        } catch (Exception exception) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(exception.getMessage())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean isDiscountValid(Double value) {
        Double min = discountService.findByName(MIN_DISCOUNT).getValue();
        Double max = discountService.findByName(MAX_DISCOUNT).getValue();
        if (value > max || value < min) {
            String message = String.format("Discount should be between %f and %f", min, max);
            log.error(message);
            throw new InvalidDiscountException(message);
        }
        return true;
    }
}
