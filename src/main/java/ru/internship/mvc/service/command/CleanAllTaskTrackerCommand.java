package ru.internship.mvc.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskTracker;

@Service("cleanall")
public class CleanAllTaskTrackerCommand implements Command {

    private final TaskTracker taskTracker;

    @Autowired
    public CleanAllTaskTrackerCommand(TaskTracker taskTracker) {
        this.taskTracker = taskTracker;
    }

    @Override
    public void execute() {
        taskTracker.cleanAllTaskTracker();
    }

    @Override
    public Command createCommand(String[] args) {
        return this;
    }
}
