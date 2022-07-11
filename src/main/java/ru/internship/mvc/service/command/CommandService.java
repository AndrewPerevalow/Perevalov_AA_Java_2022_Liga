package ru.internship.mvc.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.Map;

@Service
public class CommandService {

    private final Map<String, Command> commandMap;

    @Autowired
    CommandService(@Qualifier("stop") Command stopApplicationCommand,
                   @Qualifier("printall_withoutfilter") Command printAllTasksCommand,
                   @Qualifier("printall_withfilter") Command printAllFilterTasksCommand,
                   @Qualifier("changestatus") Command changeTaskStatusCommand,
                   @Qualifier("addtask") Command addNewTaskCommand,
                   @Qualifier("removetask") Command removeTaskCommand,
                   @Qualifier("edittask") Command editTaskCommand,
                   @Qualifier("adduser") Command addNewUserCommand,
                   @Qualifier("removeuser") Command removeUserCommand,
                   @Qualifier("cleanall") Command cleanAllTaskTrackerCommand) {

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
                commandMap.get(splitCommand[0]).createCommand(args).execute();
            } else {
                throw new InputMismatchException("Incorrect input values");
            }
        } catch (ArrayIndexOutOfBoundsException exception) {
                if (commandMap.containsKey(splitCommand[0])) {
                    commandMap.get(splitCommand[0]).execute();
                } else {
                    System.err.println("Incorrect input values");
                    return "Incorrect input values";
                }
        } catch (InputMismatchException exception) {
            System.err.println(exception.getMessage());
            return "Incorrect input values";
        }
        return "Success, let's see result in console";
    }
}
