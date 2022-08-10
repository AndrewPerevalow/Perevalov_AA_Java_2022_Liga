package com.ligainternship.carwash.dto.validate.login;

import com.ligainternship.carwash.exception.InvalidLoginException;
import com.ligainternship.carwash.model.entitiy.User;
import com.ligainternship.carwash.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class ValidateLogin implements ConstraintValidator<ValidLogin, String> {

    private final UserService userService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            isLoginValid(value);
        } catch (Exception exception) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(exception.getMessage())
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean isLoginValid(String login) {
        Optional<User> optionalUser = userService.findByLogin(login);
        if (optionalUser.isPresent()) {
            String message = "User with this login is already exist";
            log.error(message);
            throw new InvalidLoginException(message);
        }
        return true;
    }
}
