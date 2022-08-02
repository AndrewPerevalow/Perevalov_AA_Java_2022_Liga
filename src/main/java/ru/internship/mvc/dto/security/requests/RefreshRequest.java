package ru.internship.mvc.dto.security.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RefreshRequest {

    @NotEmpty(message = "Refresh token should not be empty")
    private String refreshToken;
}
