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
    StrategyService(@Qualifier("stop") Strategy stopApplicationCommand,
                    @Qualifier("printall_withoutfilter") Strategy printAllTasksCommand,
                    @Qualifier("printall_withfilter") Strategy printAllFilterTasksCommand,
                    @Qualifier("changestatus") Strategy changeTaskStatusCommand,
                    @Qualifier("addtask") Strategy addNewTaskCommand,
                    @Qualifier("removetask") Strategy removeTaskCommand,
                    @Qualifier("edittask") Strategy editTaskCommand,
                    @Qualifier("adduser") Strategy addNewUserCommand,
                    @Qualifier("removeuser") Strategy removeUserCommand,
                    @Qualifier("cleanall") Strategy cleanAllTaskTrackerCommand) {

        commandMap = Map.of("stop", stopApplicationCommand,
                            "printall_withoutfilter", printAllTasksCommand,
                            "printall_withfilter", printAllFilterTasksCommand,
                            "changestatus", changeTaskStatusCommand,
                            "addtask", addNewTaskCommand,
                            "removetask", removeTaskCommand,
                            "edittask", editTaskCommand,
                            "adduser", addNewUserCommand,
                            "removeuser", removeUserCommand,
                            "cleanall", cleanAllTaskTrackerCommand
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
                throw new InputMismatchException("Incorrect input values");
            }
        } catch (InputMismatchException exception) {
            System.err.println(exception.getMessage());
            return "Incorrect input values";
        }
    }
}
