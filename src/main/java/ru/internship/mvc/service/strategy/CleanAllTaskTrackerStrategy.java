package ru.internship.mvc.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskService;

@Service("cleanall")
public class CleanAllTaskTrackerStrategy implements Strategy {

    private final TaskService taskService;

    @Autowired
    public CleanAllTaskTrackerStrategy(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public String execute(String... args) {
        return taskService.cleanAllTaskTracker();
    }
}
