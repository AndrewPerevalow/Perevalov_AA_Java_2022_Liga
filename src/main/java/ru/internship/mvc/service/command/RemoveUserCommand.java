package ru.internship.mvc.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskTracker;

@Service("removeuser")
public class RemoveUserCommand implements Command {

    private final TaskTracker taskTracker;

    @Autowired
    public RemoveUserCommand(TaskTracker taskTracker) {
        this.taskTracker = taskTracker;
    }

    @Override
    public void execute(String... args) {
        taskTracker.removeUser(Integer.parseInt(args[0]));
    }
}
