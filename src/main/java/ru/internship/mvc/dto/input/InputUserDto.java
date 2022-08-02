package ru.internship.mvc.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
public class InputUserDto {

    @NotEmpty(message = "Name should not be empty")
    @Pattern(regexp = "[A-Za-zА-Яа-я]{3,29}", message = "Wrong name")
    private String name;

    @NotEmpty(message = "Surname should not be empty")
    @Pattern(regexp = "[A-Za-zА-Яа-я]{3,29}", message = "Wrong surname")
    private String surname;

    @NotEmpty(message = "Patronymic should not be empty")
    @Pattern(regexp = "[A-Za-zА-Яа-я]{3,29}", message = "Wrong patronymic")
    private String patronymic;

    @NotEmpty(message = "Login should not be empty")
    @Pattern(regexp = "[A-Za-zА-Яа-я]{3,29}", message = "Wrong login")
    private String login;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Wrong email")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    private String password;

    @NotEmpty(message = "Role should not be empty")
    private List<Long> roles;
}
