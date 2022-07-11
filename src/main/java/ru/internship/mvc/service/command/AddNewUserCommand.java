package ru.internship.mvc.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.UserTracker;

@Service("adduser")
public class AddNewUserCommand implements Command {

    private final UserTracker userTracker;

    private String name;

    @Autowired
    public AddNewUserCommand(UserTracker userTracker) {
        this.userTracker = userTracker;
    }

    @Override
    public void execute() {
        userTracker.addNewUser(name);
    }

    @Override
    public Command createCommand(String[] args) {
        this.name = args[0];
        return this;
    }
}