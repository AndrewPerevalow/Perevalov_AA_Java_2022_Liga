package ru.internship.mvc.service.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskService;

import java.util.InputMismatchException;

@Service("changestatus")
@RequiredArgsConstructor
public class ChangeTaskStatusStrategy implements Strategy {

    private final static String COMMAND = "changestatus";
    private final static int COUNT_ARGS = 2;

    private final TaskService taskService;

    public static String getCommand() {
        return COMMAND;
    }

    @Override
    public String execute(String... args) {
        if (args.length != COUNT_ARGS) {
            throw new InputMismatchException("Not all data entered");
        }
        Long idTask = Long.parseLong(args[0]);
        String newStatus = args[1];
        taskService.changeTaskStatus(idTask, newStatus);
        return "Status task id = "+ idTask + " changed to: " + newStatus;
    }
}

