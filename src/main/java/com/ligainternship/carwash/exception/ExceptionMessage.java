package com.ligainternship.carwash.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionMessage {
    private int statusCode;
    private String message;
    private LocalDateTime time;
}
