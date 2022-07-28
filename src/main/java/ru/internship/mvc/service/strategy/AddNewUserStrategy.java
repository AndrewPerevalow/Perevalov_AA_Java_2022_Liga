package ru.internship.mvc.service.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.internship.mvc.model.Project;
import ru.internship.mvc.model.User;
import ru.internship.mvc.repo.ProjectRepo;
import ru.internship.mvc.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

@Service("adduser")
@RequiredArgsConstructor
public class AddNewUserStrategy implements Strategy {

    private final static String COMMAND = "adduser";
    private final static int COUNT_ARGS = 7;

    private final UserService userService;
    private final ProjectRepo projectRepo;

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
        Project project = projectRepo.findById(idProject)
                .orElseThrow(() -> new EntityNotFoundException("Project with this id doesn't exist"));
        List<Project> projectList = new ArrayList<>();
        projectList.add(project);
        if (isValidInput(name, surname, patronymic, login, email, password)) {
            User newUser = new User();
            newUser.setName(name);
            newUser.setSurname(surname);
            newUser.setPatronymic(patronymic);
            newUser.setLogin(login);
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setProjects(projectList);
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