package ru.internship.mvc.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskTracker;

@Service("changestatus")
public class ChangeTaskStatusCommand implements Command {

    private final TaskTracker taskTracker;

    @Autowired
    public ChangeTaskStatusCommand(TaskTracker taskTracker) {
        this.taskTracker = taskTracker;
    }

    @Override
    public void execute(String... args) {
        taskTracker.changeTaskStatus(Integer.parseInt(args[0]), args[1]);
    }
}

