package ru.internship.mvc.service.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskService;

@Service("stop")
@RequiredArgsConstructor
public class StopApplicationStrategy implements Strategy {

    private final static String COMMAND = "stop";

    private final TaskService taskService;

    public static String getCommand() {
        return COMMAND;
    }

    @Override
    public String execute(String...args) {
        taskService.stopApplication();
        return null;
    }
}
