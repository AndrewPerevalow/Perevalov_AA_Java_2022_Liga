package ru.internship.mvc.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskTracker;

@Service("changestatus")
public class ChangeTaskStatusCommand implements Command {

    private final TaskTracker taskTracker;

    private int idTask;
    private String newStatus;

    @Autowired
    public ChangeTaskStatusCommand(TaskTracker taskTracker) {
        this.taskTracker = taskTracker;
    }

    @Override
    public void execute() {
        taskTracker.changeTaskStatus(idTask, newStatus);
    }

    @Override
    public Command createCommand(String[] args) {
        this.idTask = Integer.parseInt(args[0]);
        this.newStatus = args[1];
        return this;
    }
}

