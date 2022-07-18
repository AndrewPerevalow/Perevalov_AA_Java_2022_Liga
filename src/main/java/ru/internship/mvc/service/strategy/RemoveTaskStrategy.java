package ru.internship.mvc.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskService;

@Service("removetask")
public class RemoveTaskStrategy implements Strategy {

    private final TaskService taskService;

    @Autowired
    public RemoveTaskStrategy(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public String execute(String... args) {
        Long id = Long.parseLong(args[0]);
        return taskService.removeTask(id);
    }
}
