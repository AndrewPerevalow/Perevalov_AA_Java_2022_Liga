package ru.internship.mvc.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskTracker;

@Service("adduser")
public class AddNewUserCommand implements Command {

    private final TaskTracker taskTracker;

    @Autowired
    public AddNewUserCommand(TaskTracker taskTracker) {
        this.taskTracker = taskTracker;
    }

    @Override
    public void execute(String... args) {
        taskTracker.addNewUser(args[0]);
    }
}
