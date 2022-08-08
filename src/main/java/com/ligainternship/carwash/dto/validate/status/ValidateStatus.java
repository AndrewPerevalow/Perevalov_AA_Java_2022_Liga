package com.ligainternship.carwash.dto.validate.status;

import com.ligainternship.carwash.exception.InvalidStatusException;
import com.ligainternship.carwash.model.enums.Status;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class ValidateStatus implements ConstraintValidator<ValidStatus, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            isStatusValid(value);
        } catch (Exception exception) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(exception.getMessage())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean isStatusValid(String status) {
        if (!status.equals(Status.CANCEL.getStatus())) {
            String message = "If you want cancel booking, status should be " + Status.CANCEL.getStatus();
            log.error(message);
            throw new InvalidStatusException(message);
        }
        return true;
    }
}
