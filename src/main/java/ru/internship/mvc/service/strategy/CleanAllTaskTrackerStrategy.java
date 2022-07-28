package ru.internship.mvc.service.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskService;

@Service("cleanall")
@RequiredArgsConstructor
public class CleanAllTaskTrackerStrategy implements Strategy {

    private final static String COMMAND = "cleanall";

    private final TaskService taskService;

    public static String getCommand() {
        return COMMAND;
    }

    @Override
    public String execute(String... args) {
        return taskService.cleanAllTaskTracker();
    }
}
