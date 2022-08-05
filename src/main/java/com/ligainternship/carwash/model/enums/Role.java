package com.ligainternship.carwash.model.enums;

public enum Role {
    ADMIN("ROLE_ADMIN"),
    OPERATOR("ROLE_OPERATOR"),
    USER("ROLE_USER"),
    ANONYMOUS("ROLE_ANONYMOUS");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
