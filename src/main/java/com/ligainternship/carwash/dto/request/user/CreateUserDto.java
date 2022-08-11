package com.ligainternship.carwash.dto.request.user;

import com.ligainternship.carwash.dto.validate.login.ValidLogin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {

    @NotEmpty(message = "Name should not be empty")
    @Pattern(regexp = "[A-Za-zА-Яа-я]{3,29}", message = "Wrong name")
    private String name;

    @NotEmpty(message = "Surname should not be empty")
    @Pattern(regexp = "[A-Za-zА-Яа-я]{3,29}", message = "Wrong surname")
    private String surname;

    @NotEmpty(message = "Login should not be empty")
    @ValidLogin(message = "Login should be unique")
    private String login;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Wrong email")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    private String password;

    @NotEmpty(message = "Phone number should not be empty")
    @Pattern(regexp = "^89(\\d){9}$", message = "Phone number should start from 89 and have length 11")
    private String phoneNumber;
}
