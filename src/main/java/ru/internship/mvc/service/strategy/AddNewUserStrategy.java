package ru.internship.mvc.service.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.internship.mvc.dto.input.InputUserDto;
import ru.internship.mvc.service.UserService;

import java.util.InputMismatchException;

@Service("add_user")
@RequiredArgsConstructor
public class AddNewUserStrategy implements Strategy {

    private final static String COMMAND = "add_user";
    private final static int COUNT_ARGS = 7;

    private final UserService userService;

    public static String getCommand() {
        return COMMAND;
    }

    @Override
    public String execute(String... args) {
        if (args.length != COUNT_ARGS) {
            throw new InputMismatchException("Not all data entered");
        }
        String name = args[0];
        String surname = args[1];
        String patronymic = args[2];
        String login = args[3];
        String email = args[4];
        String password = args[5];
        Long idProject = Long.parseLong(args[6]);
        if (isValidInput(name, surname, patronymic, login, email, password)) {
            InputUserDto newUser = new InputUserDto();
            newUser.setName(name);
            newUser.setSurname(surname);
            newUser.setPatronymic(patronymic);
            newUser.setLogin(login);
            newUser.setEmail(email);
            newUser.setPassword(password);
            userService.addNewUser(idProject, newUser);
        return "New user: " + name + " saved";
        } else {
            throw new InputMismatchException("Invalid input");
        }
    }

    private boolean isValidInput(String name, String surname, String patronymic, String login, String email, String password) {
        if (name == null) return false;
        if (name.trim().length() == 0) return false;
        if (surname == null) return false;
        if (surname.trim().length() == 0) return false;
        if (patronymic == null) return false;
        if (patronymic.trim().length() == 0) return false;
        if (login == null) return false;
        if (login.trim().length() == 0) return false;
        if (email == null) return false;
        if (email.trim().length() == 0) return false;
        if (password == null) return false;
       return password.trim().length() != 0;
    }
}