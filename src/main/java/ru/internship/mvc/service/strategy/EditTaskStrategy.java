package ru.internship.mvc.service.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.internship.mvc.model.Project;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.User;
import ru.internship.mvc.repo.ProjectRepo;
import ru.internship.mvc.repo.TaskRepo;
import ru.internship.mvc.repo.UserRepo;
import ru.internship.mvc.service.TaskService;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;

@Service("edittask")
@RequiredArgsConstructor
public class EditTaskStrategy implements Strategy {

    private final static String COMMAND = "edittask";
    private final static int COUNT_ARGS = 6;

    private final TaskService taskService;
    private final TaskRepo taskRepo;
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
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with this id doesn't exist"));
        User user = userRepo.findById(idUser)
                .orElseThrow(() -> new EntityNotFoundException("User with this id doesn't exist"));
        Project project = projectRepo.findById(idUser)
                .orElseThrow(() -> new EntityNotFoundException("Project with this id doesn't exist"));
        if (isValidInput(header, description, deadline)) {
            task.setHeader(header);
            task.setDescription(description);
            task.setUser(user);
            task.setDeadline(deadline);
            task.setProject(project);
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
