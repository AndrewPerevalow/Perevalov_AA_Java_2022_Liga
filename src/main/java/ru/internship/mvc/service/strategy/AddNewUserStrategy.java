package ru.internship.mvc.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.model.User;
import ru.internship.mvc.service.UserService;

@Service("adduser")
public class AddNewUserStrategy implements Strategy {

    private final UserService userService;

    @Autowired
    public AddNewUserStrategy(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(String... args) {
        String name = args[0];
        User newUser = new User();
        newUser.setName(name);
        userService.addNewUser(newUser);
        return "New user: " + name + " saved";
    }
}