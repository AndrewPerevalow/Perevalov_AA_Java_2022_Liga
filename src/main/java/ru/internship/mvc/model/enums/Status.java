package ru.internship.mvc.model.enums;

public enum Status {

    DEFAULT_STATUS("Новое"),
    WORK_STATUS("В работе"),
    COMPLETE_STATUS("Готово");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
