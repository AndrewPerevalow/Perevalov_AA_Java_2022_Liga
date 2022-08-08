package com.ligainternship.carwash.model.enums;

public enum Roles {
    ADMIN("ROLE_ADMIN"),
    OPERATOR("ROLE_OPERATOR"),
    USER("ROLE_USER"),
    ANONYMOUS("ROLE_ANONYMOUS");

    private final String role;

    Roles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
