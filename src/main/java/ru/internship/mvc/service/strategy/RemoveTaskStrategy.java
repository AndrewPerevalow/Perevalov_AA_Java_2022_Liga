package ru.internship.mvc.service.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskService;

import java.util.InputMismatchException;

@Service("removetask")
@RequiredArgsConstructor
public class RemoveTaskStrategy implements Strategy {

    private final static String COMMAND = "removetask";
    private final static int COUNT_ARGS = 1;

    private final TaskService taskService;

    public static String getCommand() {
        return COMMAND;
    }

    @Override
    public String execute(String... args) {
        if (args.length != COUNT_ARGS) {
            throw new InputMismatchException("Not all data entered");
        }
        Long id = Long.parseLong(args[0]);
        return taskService.removeTask(id);
    }
}
