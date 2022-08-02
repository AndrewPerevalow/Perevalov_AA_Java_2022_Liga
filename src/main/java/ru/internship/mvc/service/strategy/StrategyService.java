package ru.internship.mvc.service.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.internship.mvc.util.ParseCommand;

import javax.persistence.EntityNotFoundException;
import java.util.InputMismatchException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StrategyService {

    private final Map<String, Strategy> commandMap;

    public String executeCommand(String input) {
        try {
            String command = ParseCommand.getCommand(input);
            String[] args = ParseCommand.getArgs(input);
            if (commandMap.containsKey(command)) {
                return commandMap.get(command).execute(args);
            } else {
                return "Incorrect input values";
            }
        } catch (InputMismatchException | EntityNotFoundException exception) {
            return exception.getMessage();
        }
    }

    public String executeFind(String status, String dateInterval) {
        try {
            return commandMap.get(FindByMaxTasksCountStrategy.getCommand()).execute(status, dateInterval);
        } catch (InputMismatchException | EntityNotFoundException exception) {
            return exception.getMessage();
        }
    }
}
