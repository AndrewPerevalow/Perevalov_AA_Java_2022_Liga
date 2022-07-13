package ru.internship.mvc.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskTracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("edittask")
public class EditTaskStrategy implements Strategy {

    private final TaskTracker taskTracker;

    @Autowired
    public EditTaskStrategy(TaskTracker taskTracker) {
        this.taskTracker = taskTracker;
    }

    @Override
    public String execute(String...args) {
        Date dateDeadline = null;
        try {
            dateDeadline = new SimpleDateFormat("dd.MM.yyyy").parse(args[4]);
        } catch (ParseException exception) {
            System.err.println("Parse fail: " + exception.getMessage());
        }
        return taskTracker.editTask(Integer.parseInt(args[0]), args[1], args[2], Integer.parseInt(args[3]), dateDeadline);
    }
}
