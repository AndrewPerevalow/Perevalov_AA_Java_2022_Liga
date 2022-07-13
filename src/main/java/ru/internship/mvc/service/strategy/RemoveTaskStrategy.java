package ru.internship.mvc.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskTracker;

@Service("removetask")
public class RemoveTaskStrategy implements Strategy {

    private final TaskTracker taskTracker;

    @Autowired
    public RemoveTaskStrategy(TaskTracker taskTracker) {
        this.taskTracker = taskTracker;
    }

    @Override
    public String execute(String...args) {
         return taskTracker.removeTask(Integer.parseInt(args[0]));
    }
}
