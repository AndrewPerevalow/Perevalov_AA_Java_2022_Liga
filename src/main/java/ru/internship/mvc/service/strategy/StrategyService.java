package ru.internship.mvc.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.internship.mvc.util.ParseCommand;

import javax.persistence.EntityNotFoundException;
import java.util.InputMismatchException;
import java.util.Map;

@Service
public class StrategyService {

    private final Map<String, Strategy> commandMap;
    private final Strategy findByMaxTasksCountStrategy;

    @Autowired
    public StrategyService(@Qualifier("stop") Strategy stopApplicationStrategy,
                           @Qualifier("printall_withoutfilter") Strategy printAllTasksStrategy,
                           @Qualifier("printall_withfilter") Strategy printAllFilterTasksStrategy,
                           @Qualifier("changestatus") Strategy changeTaskStatusStrategy,
                           @Qualifier("addtask") Strategy addNewTaskStrategy,
                           @Qualifier("removetask") Strategy removeTaskStrategy,
                           @Qualifier("edittask") Strategy editTaskStrategy,
                           @Qualifier("adduser") Strategy addNewUserStrategy,
                           @Qualifier("removeuser") Strategy removeUserStrategy,
                           @Qualifier("cleanall") Strategy cleanAllTaskTrackerStrategy,
                           @Qualifier("find_user_by_max_count_tasks") Strategy findByMaxTasksCountStrategy) {

        commandMap = Map.of
        (
                StopApplicationStrategy.getCommand(), stopApplicationStrategy,
                PrintAllTaskTrackerImplStrategy.getCommand(), printAllTasksStrategy,
                FilterAllTasksForUsersByStatusStrategy.getCommand(), printAllFilterTasksStrategy,
                ChangeTaskStatusStrategy.getCommand(), changeTaskStatusStrategy,
                AddNewTaskStrategy.getCommand(), addNewTaskStrategy,
                RemoveTaskStrategy.getCommand(), removeTaskStrategy,
                EditTaskStrategy.getCommand(), editTaskStrategy,
                AddNewUserStrategy.getCommand(), addNewUserStrategy,
                RemoveUserStrategy.getCommand(), removeUserStrategy,
                CleanAllTaskTrackerStrategy.getCommand(), cleanAllTaskTrackerStrategy
        );
        this.findByMaxTasksCountStrategy = findByMaxTasksCountStrategy;
    }

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
            return findByMaxTasksCountStrategy.execute(status, dateInterval);
        } catch (InputMismatchException | EntityNotFoundException exception) {
            return exception.getMessage();
        }
    }
}
