package ru.internship.mvc.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskTracker;

@Service("stop")
public class StopApplicationCommand implements Command {

    private final TaskTracker taskTracker;

    @Autowired
    public StopApplicationCommand(TaskTracker taskTracker) {
        this.taskTracker = taskTracker;
    }

    @Override
    public void execute(String... args) {
        taskTracker.stopApplication();
    }
}