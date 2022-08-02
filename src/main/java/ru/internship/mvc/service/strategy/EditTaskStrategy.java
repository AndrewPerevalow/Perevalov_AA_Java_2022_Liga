package ru.internship.mvc.service.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.internship.mvc.dto.input.InputTaskDto;
import ru.internship.mvc.service.TaskService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;

@Service("edit_task")
@RequiredArgsConstructor
public class EditTaskStrategy implements Strategy {

    private final static String COMMAND = "edit_task";
    private final static int COUNT_ARGS = 6;

    private final TaskService taskService;

    public static String getCommand() {
        return COMMAND;
    }

    @Override
    public String execute(String... args) {
        if (args.length != COUNT_ARGS) {
            throw new InputMismatchException("Not all data entered");
        }
        Long id = Long.parseLong(args[0]);
        String header = args[1];
        String description = args[2];
        Long idUser = Long.parseLong(args[3]);
        Date deadline;
        Long idProject = Long.parseLong(args[5]);
        try {
            deadline = new SimpleDateFormat("yyyy-MM-dd").parse(args[4]);
        } catch (ParseException exception) {
            return "Parse fail: " + exception.getMessage();
        }
        if (isValidInput(header, description, deadline)) {
            InputTaskDto task = new InputTaskDto();
            task.setHeader(header);
            task.setDescription(description);
            task.setDeadline(deadline);
        taskService.editTask(id, idUser, idProject, task);
            return "Task: " + id + " edited";
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
