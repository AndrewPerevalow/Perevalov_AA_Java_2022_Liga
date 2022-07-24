package ru.internship.mvc.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("edittask")
public class EditTaskStrategy implements Strategy {

    private final TaskService taskService;

    @Autowired
    public EditTaskStrategy(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public String execute(String... args) {
        Long id = Long.parseLong(args[0]);
        String header = args[1];
        String description = args[2];
        Long idUser = Long.parseLong(args[3]);
        Date deadline;
        try {
            deadline = new SimpleDateFormat("yyyy-MM-dd").parse(args[4]);
        } catch (ParseException exception) {
            return "Parse fail: " + exception.getMessage();
        }
        taskService.editTask(id, header, description, idUser, deadline);
        return "Task: " + id + " edited";
    }
}
