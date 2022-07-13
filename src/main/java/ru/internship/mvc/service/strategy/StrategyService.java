package ru.internship.mvc.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.Map;

@Service
public class StrategyService {

    private final Map<String, Strategy> commandMap;

    @Autowired
    StrategyService(@Qualifier("stop") Strategy stopApplicationStrategy,
                    @Qualifier("printall_withoutfilter") Strategy printAllTasksStrategy,
                    @Qualifier("printall_withfilter") Strategy printAllFilterTasksStrategy,
                    @Qualifier("changestatus") Strategy changeTaskStatusStrategy,
                    @Qualifier("addtask") Strategy addNewTaskStrategy,
                    @Qualifier("removetask") Strategy removeTaskStrategy,
                    @Qualifier("edittask") Strategy editTaskStrategy,
                    @Qualifier("adduser") Strategy addNewUserStrategy,
                    @Qualifier("removeuser") Strategy removeUserStrategy,
                    @Qualifier("cleanall") Strategy cleanAllTaskTrackerStrategy) {

        commandMap = Map.of("stop", stopApplicationStrategy,
                            "printall_withoutfilter", printAllTasksStrategy,
                            "printall_withfilter", printAllFilterTasksStrategy,
                            "changestatus", changeTaskStatusStrategy,
                            "addtask", addNewTaskStrategy,
                            "removetask", removeTaskStrategy,
                            "edittask", editTaskStrategy,
                            "adduser", addNewUserStrategy,
                            "removeuser", removeUserStrategy,
                            "cleanall", cleanAllTaskTrackerStrategy
        );
    }

    public String executeCommand(String command) {
        String[] splitCommand = command.split(" ", 2);
        try {
            String[] args = splitCommand[1].split(",");
            if (commandMap.containsKey(splitCommand[0])) {
                return commandMap.get(splitCommand[0]).execute(args);
            } else {
                throw new InputMismatchException("Incorrect input values");
            }
        } catch (ArrayIndexOutOfBoundsException exception) {
            if (commandMap.containsKey(splitCommand[0])) {
                return commandMap.get(splitCommand[0]).execute();
            } else {
                return "Incorrect input values";
            }
        } catch (InputMismatchException exception) {
            System.err.println(exception.getMessage());
            return "Incorrect input values";
        }
    }
}
