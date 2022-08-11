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
        if (!status.equals(Status.ACTIVE.getStatus())
                && !status.equals(Status.CANCEL.getStatus())
                && !status.equals(Status.COMPLETE.getStatus())) {
            String message = String.format("Invalid status. Three vars of the status: %s, %s, %s",
                    Status.ACTIVE.getStatus(),
                    Status.CANCEL.getStatus(),
                    Status.COMPLETE.getStatus()
            );
            log.error(message);
            throw new InvalidStatusException(message);
        }
        return true;
    }
}