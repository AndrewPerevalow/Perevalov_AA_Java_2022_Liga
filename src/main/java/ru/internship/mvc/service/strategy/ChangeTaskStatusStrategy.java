package ru.internship.mvc.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskTracker;

@Service("changestatus")
public class ChangeTaskStatusStrategy implements Strategy {

    private final TaskTracker taskTracker;

    @Autowired
    public ChangeTaskStatusStrategy(TaskTracker taskTracker) {
        this.taskTracker = taskTracker;
    }

    @Override
    public String execute(String...args) {
        return taskTracker.changeTaskStatus(Integer.parseInt(args[0]), args[1]);
    }
}

