package ru.internship.mvc.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.service.TaskService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("addtask")
public class AddNewTaskStrategy implements Strategy {

    private static String DEFAULT_STATUS;

    @Value("${statuses.default-status}")
    public void setDefaultStatus(String status) {
        AddNewTaskStrategy.DEFAULT_STATUS = status;
    }

    private final TaskService taskService;

    @Autowired
    public AddNewTaskStrategy(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public String execute(String... args) {
        String header = args[0];
        String description = args[1];
        Long idUser = Long.parseLong(args[2]);
        Date deadline;
        try {
            deadline = new SimpleDateFormat("yyyy-MM-dd").parse(args[3]);
        } catch (ParseException exception) {
            return "Parse fail: " + exception.getMessage();
        }
        Task newTask = new Task();
        newTask.setHeader(header);
        newTask.setDescription(description);
        newTask.setIdUser(idUser);
        newTask.setDeadline(deadline);
        newTask.setStatus(DEFAULT_STATUS);
        return "Added task: " + taskService.addNewTask(newTask).toString();
    }
}
