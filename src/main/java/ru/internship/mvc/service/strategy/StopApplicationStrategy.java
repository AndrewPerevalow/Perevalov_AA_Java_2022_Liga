package ru.internship.mvc.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskTracker;

@Service("stop")
public class StopApplicationStrategy implements Strategy {

    private final TaskTracker taskTracker;

    @Autowired
    public StopApplicationStrategy(TaskTracker taskTracker) {
        this.taskTracker = taskTracker;
    }

    @Override
    public String execute(String...args) {
        taskTracker.stopApplication();
        return null;
    }
}
