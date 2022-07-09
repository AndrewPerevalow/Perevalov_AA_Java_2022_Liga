package ru.internship.mvc.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskTracker;

@Service("printall_withfilter")
public class FilterAllTasksForUsersByStatusCommand implements Command {

    private final TaskTracker taskTracker;

    @Autowired
    public FilterAllTasksForUsersByStatusCommand(TaskTracker taskTracker) {
        this.taskTracker = taskTracker;
    }

    @Override
    public void execute(String... args) {
        taskTracker.filterAllTasksForUsersByStatus(Integer.parseInt(args[0]), args[1]);
    }
}
