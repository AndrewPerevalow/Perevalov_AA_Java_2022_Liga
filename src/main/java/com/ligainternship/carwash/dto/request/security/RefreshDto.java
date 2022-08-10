package com.ligainternship.carwash.dto.request.security;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RefreshDto {

    @NotEmpty(message = "Refresh token should not be empty")
    private String refreshToken;
}
