package com.ligainternship.carwash.dto.request.security;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginDto {

    @NotEmpty(message = "Login should not be empty")
    private String login;

    @NotEmpty(message = "Password should not be empty")
    private String password;
}
