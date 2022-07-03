package ru.internship.mvc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.internship.mvc.service.TaskTracker;


import javax.annotation.PostConstruct;
import java.util.InputMismatchException;


@RestController
public class Controller {
    private final TaskTracker taskTracker;

    @Autowired
    Controller(TaskTracker taskTracker) {
        this.taskTracker = taskTracker;
    }

    @PostConstruct
    public void firstInit() {
        taskTracker.getAllTasksForUsers();
    }

    @GetMapping("/cli")
    public String getCommand(@RequestParam String command) {
        String[] splitCommand = command.split(" ", 2);
        try {
            switch (splitCommand[0]) {
                case "stop" -> System.exit(0);

                case "printall" -> {
                    String[] arguments = splitCommand[1].split(",");

                    switch (arguments[0]) {
                        case "withoutfilter" -> taskTracker.printAllTasksForUsers(Integer.parseInt(arguments[1]));

                        case "withfilter" -> taskTracker.filterAllTasksForUsersByStatus(Integer.parseInt(arguments[1]), arguments[2]);

                        default -> throw new InputMismatchException();
                    }
                }

                case "changestatus" -> {
                    String[] arguments = splitCommand[1].split(",");
                    taskTracker.changeTaskStatus(Integer.parseInt(arguments[0]), arguments[1]);
                }

                case "addtask" -> {
                    String[] arguments = splitCommand[1].split(",");
                    taskTracker.addNewTask(arguments[0], arguments[1], Integer.parseInt(arguments[2]), arguments[3]);
                }

                case "removetask" -> taskTracker.removeTask(Integer.parseInt(splitCommand[1]));

                case "edittask" -> {
                    String[] arguments = splitCommand[1].split(",");
                    taskTracker.editTask(Integer.parseInt(arguments[0]), arguments[1], arguments[2], Integer.parseInt(arguments[3]), arguments[4]);
                }

                case "adduser" -> taskTracker.addNewUser(splitCommand[1]);

                case "removeuser" -> taskTracker.removeUser(Integer.parseInt(splitCommand[1]));

                case "cleanall" -> taskTracker.cleanAllTaskTracker();

                default -> throw new InputMismatchException();
            }
        } catch (InputMismatchException | ArrayIndexOutOfBoundsException | NumberFormatException exception) {
            System.err.println("Incorrect input values");
            return "Incorrect input values";
        }
        return "Success, let's see result in console";
    }
}
