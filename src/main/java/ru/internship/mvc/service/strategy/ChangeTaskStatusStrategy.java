package ru.internship.mvc.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskService;

@Service("changestatus")
public class ChangeTaskStatusStrategy implements Strategy {

    private final TaskService taskService;

    @Autowired
    public ChangeTaskStatusStrategy(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public String execute(String... args) {
        Long idTask = Long.parseLong(args[0]);
        String newStatus = args[1];
        return taskService.changeTaskStatus(idTask, newStatus);
    }
}

