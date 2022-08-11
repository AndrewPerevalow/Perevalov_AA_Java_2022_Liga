package com.ligainternship.carwash.dto.request.security;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class RefreshDto {

    @NotEmpty(message = "Refresh token should not be empty")
    private String refreshToken;
}
