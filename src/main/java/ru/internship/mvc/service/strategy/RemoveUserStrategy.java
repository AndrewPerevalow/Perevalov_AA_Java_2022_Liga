package ru.internship.mvc.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.UserService;

@Service("removeuser")
public class RemoveUserStrategy implements Strategy {

    private final UserService userService;

    @Autowired
    public RemoveUserStrategy(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(String... args) {
        Long id = Long.parseLong(args[0]);
        return userService.removeUser(id);
    }
}
