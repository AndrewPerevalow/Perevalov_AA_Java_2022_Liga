package com.ligainternship.carwash.model.enums;

public enum Status {

    ACTIVE("Active"),
    CANCEL("Cancel"),
    COMPLETE("Complete");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
