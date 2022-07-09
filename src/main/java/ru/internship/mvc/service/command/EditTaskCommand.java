package ru.internship.mvc.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskTracker;

@Service("edittask")
public class EditTaskCommand implements Command {

    private final TaskTracker taskTracker;

    @Autowired
    public EditTaskCommand(TaskTracker taskTracker) {
        this.taskTracker = taskTracker;
    }

    @Override
    public void execute(String... args) {
        taskTracker.editTask(Integer.parseInt(args[0]), args[1], args[2], Integer.parseInt(args[3]), args[4]);
    }
}
