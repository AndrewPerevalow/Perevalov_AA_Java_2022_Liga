package ru.internship.mvc.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskTracker;

@Service("edittask")
public class EditTaskCommand implements Command {

    private final TaskTracker taskTracker;

    private int idTask;
    private String header;
    private String description;
    private int idUser;
    private String deadline;

    @Autowired
    public EditTaskCommand(TaskTracker taskTracker) {
        this.taskTracker = taskTracker;
    }

    @Override
    public void execute() {
        taskTracker.editTask(idTask, header, description, idUser, deadline);
    }

    @Override
    public Command createCommand(String[] args) {
        this.idTask = Integer.parseInt(args[0]);
        this.header = args[1];
        this.description = args[2];
        this.idUser = Integer.parseInt(args[3]);
        this.deadline = args[4];
        return this;
    }
}
