package ru.internship.mvc.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskService;

@Service("stop")
public class StopApplicationStrategy implements Strategy {

    private final TaskService taskService;

    @Autowired
    public StopApplicationStrategy(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public String execute(String...args) {
        taskService.stopApplication();
        return null;
    }
}
