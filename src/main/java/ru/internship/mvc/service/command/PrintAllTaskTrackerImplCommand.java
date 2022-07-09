package ru.internship.mvc.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskTracker;

@Service("printall_withoutfilter")
public class PrintAllTaskTrackerImplCommand implements Command {

    private final TaskTracker taskTracker;

    @Autowired
    public PrintAllTaskTrackerImplCommand(TaskTracker taskTracker) {
        this.taskTracker = taskTracker;
    }

    @Override
    public void execute(String... args) {
        taskTracker.printAllTasksForUsers(Integer.parseInt(args[0]));
    }
}
