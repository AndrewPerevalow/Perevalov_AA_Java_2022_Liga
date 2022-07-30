package ru.internship.mvc.service.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.internship.mvc.dto.InputTaskDto;
import ru.internship.mvc.model.enums.Status;
import ru.internship.mvc.service.TaskService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;

@Service("addtask")
@RequiredArgsConstructor
public class AddNewTaskStrategy implements Strategy {

    private final static String COMMAND = "addtask";
    private final static int COUNT_ARGS = 5;

    private final TaskService taskService;

    public static String getCommand() {
        return COMMAND;
    }

    @Override
    public String execute(String... args) {
        if (args.length != COUNT_ARGS) {
            throw new InputMismatchException("Not all data entered");
        }
        String header = args[0];
        String description = args[1];
        Long idUser = Long.parseLong(args[2]);
        Date deadline;
        Long idProject = Long.parseLong(args[4]);
        try {
            deadline = new SimpleDateFormat("yyyy-MM-dd").parse(args[3]);
        } catch (ParseException exception) {
            return "Parse fail: " + exception.getMessage();
        }
        if (isValidInput(header, description, deadline)) {
            InputTaskDto newTask = new InputTaskDto();
            newTask.setHeader(header);
            newTask.setDescription(description);
            newTask.setDeadline(deadline);
            newTask.setStatus(Status.DEFAULT_STATUS.getStatus());
            return "Added task: " + taskService.addNewTask(idUser, idProject, newTask).toString();
        } else {
            throw new InputMismatchException("Incorrect input values");
        }
    }

    private boolean isValidInput(String header, String description, Date deadline) {
        if (header == null) return false;
        if (header.trim().length() == 0) return false;
        if (description == null) return false;
        if (description.trim().length() == 0) return false;
        return isDateValid(deadline);
    }

    private boolean isDateValid(Date inputDate) {
        if (inputDate == null) return false;
        Date currentDate = new Date();
        int result = inputDate.compareTo(currentDate);
        return result >= 0;
    }
}
