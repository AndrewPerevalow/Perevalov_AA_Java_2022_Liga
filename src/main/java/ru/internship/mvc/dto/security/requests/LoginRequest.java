package ru.internship.mvc.dto.security.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class LoginRequest {

    @NotEmpty(message = "Name should not be empty")
    @Pattern(regexp = "[A-Za-zА-Яа-я]{3,29}", message = "Wrong name")
    private String username;

    @NotEmpty(message = "Password should not be empty")
    private String password;
}
