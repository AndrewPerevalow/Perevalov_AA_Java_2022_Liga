package ru.internship.mvc.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.UserTracker;

@Service("removeuser")
public class RemoveUserCommand implements Command {

    private final UserTracker userTracker;

    private int idUser;

    @Autowired
    public RemoveUserCommand(UserTracker userTracker) {
        this.userTracker = userTracker;
    }

    @Override
    public void execute() {
        userTracker.removeUser(idUser);
    }

    @Override
    public Command createCommand(String[] args) {
        this.idUser = Integer.parseInt(args[0]);
        return this;
    }
}
