package com.ligainternship.carwash.rest.exception;

import com.ligainternship.carwash.exception.ExceptionMessage;
import com.ligainternship.carwash.exception.InvalidInputException;
import com.ligainternship.carwash.exception.InvalidTimeException;
import com.ligainternship.carwash.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandleController {

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ExceptionMessage> handleInvalidTimeException(InvalidInputException exception) {
        return new ResponseEntity<>(
                new ExceptionMessage(
                        HttpStatus.BAD_REQUEST.value(),
                        exception.getMessages().toString(),
                        LocalDateTime.now()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>(
                new ExceptionMessage(
                        HttpStatus.BAD_REQUEST.value(),
                        exception.getMessage(),
                        LocalDateTime.now()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    /*@ExceptionHandler(InvalidTimeException.class)
    public ResponseEntity<ExceptionMessage> handleUserNotFoundException(InvalidTimeException exception) {
        return new ResponseEntity<>(
                new ExceptionMessage(
                        HttpStatus.BAD_REQUEST.value(),
                        exception.getMessage(),
                        LocalDateTime.now()
                ),
                HttpStatus.BAD_REQUEST
        );
    }*/
}
