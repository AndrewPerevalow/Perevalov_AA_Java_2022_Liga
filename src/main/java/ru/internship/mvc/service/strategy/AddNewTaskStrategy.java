package ru.internship.mvc.service.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.internship.mvc.model.Project;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.User;
import ru.internship.mvc.model.enums.Status;
import ru.internship.mvc.repo.ProjectRepo;
import ru.internship.mvc.repo.UserRepo;
import ru.internship.mvc.service.TaskService;

import javax.persistence.EntityNotFoundException;
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
    private final UserRepo userRepo;
    private final ProjectRepo projectRepo;

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
        User user = userRepo.findById(idUser)
                .orElseThrow(() -> new EntityNotFoundException("User with this id doesn't exist"));
        Project project = projectRepo.findById(idProject)
                .orElseThrow(() -> new EntityNotFoundException("Project with this id doesn't exist"));
        if (isValidInput(header, description, deadline)) {
            Task newTask = new Task();
            newTask.setHeader(header);
            newTask.setDescription(description);
            newTask.setUser(user);
            newTask.setDeadline(deadline);
            newTask.setStatus(Status.DEFAULT_STATUS.getStatus());
            newTask.setProject(project);
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
