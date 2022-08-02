package ru.internship.mvc.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorHandle {
    private int status;
    private Date date;
    private String message;
}
