package ru.internship.mvc.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.internship.mvc.dto.error.ErrorHandle;

import javax.naming.AuthenticationException;
import java.util.Date;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<?> handleTokenExceptions(AuthenticationException ex) {
        return ResponseEntity.ok(
                new ErrorHandle(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage()
                )
        );
    }
}
