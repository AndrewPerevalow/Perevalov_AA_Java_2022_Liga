package ru.internship.mvc.service.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskInfoService;

import java.util.InputMismatchException;

@Service("printall_with_filter")
@RequiredArgsConstructor
public class FilterAllTasksForUsersByStatusStrategy implements Strategy {

    private final static String COMMAND = "printall_with_filter";
    private final static int COUNT_ARGS = 2;

    private final TaskInfoService taskInfoService;

    public static String getCommand() {
        return COMMAND;
    }

    @Override
    public String execute(String... args) {
        if (args.length != COUNT_ARGS) {
            throw new InputMismatchException("Not all data entered");
        }
        Long id = Long.parseLong(args[0]);
        String status = args[1];
        return taskInfoService.filterAllTasksForUsersByStatus(id, status)
                .toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\, ", "");
    }
}
