package com.ligainternship.carwash.rest.exception;

import com.ligainternship.carwash.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandleController {

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleInvalidInputException(InvalidInputException exception) {
        return new ExceptionMessage(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessages().toString(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleUserNotFoundException(UserNotFoundException exception) {
        return new ExceptionMessage(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(OperationNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleOperationNotFoundException(OperationNotFoundException exception) {
        return new ExceptionMessage(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(BookingNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleBookingNotFoundException(BookingNotFoundException exception) {
        return new ExceptionMessage(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(DiscountNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleDiscountNotFoundException(DiscountNotFoundException exception) {
        return new ExceptionMessage(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(BoxNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleBoxNotFoundException(BoxNotFoundException exception) {
        return new ExceptionMessage(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleRoleNotFoundException(RoleNotFoundException exception) {
        return new ExceptionMessage(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleAuthenticationException(AuthenticationException exception) {
        return new ExceptionMessage(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
    }
}
