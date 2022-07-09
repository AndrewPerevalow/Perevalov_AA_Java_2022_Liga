package ru.internship.mvc.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskTracker;

@Service("removetask")
public class RemoveTaskCommand implements Command {

    private final TaskTracker taskTracker;

    @Autowired
    public RemoveTaskCommand(TaskTracker taskTracker) {
        this.taskTracker = taskTracker;
    }

    @Override
    public void execute(String... args) {
        taskTracker.removeTask(Integer.parseInt(args[0]));
    }
}
