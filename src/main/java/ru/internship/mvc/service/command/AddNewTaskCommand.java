package ru.internship.mvc.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskTracker;

@Service("addtask")
public class AddNewTaskCommand implements Command {

    private final TaskTracker taskTracker;

    private String header;
    private String description;
    private int idUser;
    private String deadline;

    @Autowired
    public AddNewTaskCommand(TaskTracker taskTracker) {
        this.taskTracker = taskTracker;
    }

    @Override
    public void execute() {
        taskTracker.addNewTask(header, description, idUser, deadline);
    }

    @Override
    public Command createCommand(String[] args) {
        this.header = args[0];
        this.description = args[1];
        this.idUser = Integer.parseInt(args[2]);
        this.deadline = args[3];
        return this;
    }
}
