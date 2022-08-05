package com.ligainternship.carwash.exception;

import java.util.List;

public class InvalidInputException extends RuntimeException {
    private final List<String> messages;

    public InvalidInputException(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }
}
