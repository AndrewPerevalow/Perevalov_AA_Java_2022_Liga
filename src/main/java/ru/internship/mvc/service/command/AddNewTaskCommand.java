package ru.internship.mvc.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskTracker;

@Service("addtask")
public class AddNewTaskCommand implements Command {

    private final TaskTracker taskTracker;

    @Autowired
    public AddNewTaskCommand(TaskTracker taskTracker) {
        this.taskTracker = taskTracker;
    }

    @Override
    public void execute(String... args) {
        taskTracker.addNewTask(args[0], args[1], Integer.parseInt(args[2]), args[3]);
    }
}
