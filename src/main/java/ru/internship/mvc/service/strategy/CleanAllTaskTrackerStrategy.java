package ru.internship.mvc.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskTracker;

@Service("cleanall")
public class CleanAllTaskTrackerStrategy implements Strategy {

    private final TaskTracker taskTracker;

    @Autowired
    public CleanAllTaskTrackerStrategy(TaskTracker taskTracker) {
        this.taskTracker = taskTracker;
    }

    @Override
    public String execute(String...args) {
        return taskTracker.cleanAllTaskTracker();
    }
}
