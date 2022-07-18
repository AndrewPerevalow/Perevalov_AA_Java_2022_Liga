package ru.internship.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.repo.TaskRepo;
import ru.internship.mvc.repo.UserRepo;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.InputMismatchException;


@Service
public class TaskService {

    private static String DEFAULT_STATUS;

    @Value("${statuses.default-status}")
    public void setDefaultStatus(String status) {
        TaskService.DEFAULT_STATUS = status;
    }

    private static String WORK_STATUS;

    @Value("${statuses.work-status}")
    public void setWorkStatus(String status) {
        TaskService.WORK_STATUS = status;
    }

    private static String COMPLETE_STATUS;

    @Value("${statuses.complete-status}")
    public void setCompleteStatus(String status) {
        TaskService.COMPLETE_STATUS = status;
    }

    private final TaskRepo taskRepo;
    private final UserRepo userRepo;

    @Autowired
    public TaskService(TaskRepo taskRepo, UserRepo userRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }

    public Task addNewTask(Task task) {
        userRepo.findById(task.getIdUser())
                .orElseThrow(() -> new EntityNotFoundException("User with this id doesn't exist"));
        if (isValidInput(task.getHeader(), task.getDescription(), task.getDeadline())) {
            return taskRepo.save(task);
        } else {
            throw new InputMismatchException("Incorrect input values");
        }
    }

    public String removeTask(Long idTask) {
        taskRepo.findById(idTask)
                .orElseThrow(() -> new EntityNotFoundException("Task with this id doesn't exist"));
        taskRepo.deleteById(idTask);
        return "Task: " + idTask + " deleted";
    }

    public String changeTaskStatus(Long idTask, String newStatus) {
        Task task = taskRepo.findById(idTask)
                .orElseThrow(() -> new EntityNotFoundException("Task with this id doesn't exist"));
        if (newStatus.equals(DEFAULT_STATUS) || newStatus.equals(WORK_STATUS) || newStatus.equals(COMPLETE_STATUS)) {
            task.setStatus(newStatus);
            taskRepo.save(task);
        } else {
            throw new InputMismatchException("Incorrect status");
        }
        return "Status task id = "+ idTask + " changed to: " + newStatus;
    }

    public String editTask(Long idTask, String header, String description, Long idUser, Date deadline) {
        userRepo.findById(idUser)
                .orElseThrow(() -> new EntityNotFoundException("User with this id doesn't exist"));
        Task task = taskRepo.findById(idTask)
                .orElseThrow(() -> new EntityNotFoundException("Task with this id doesn't exist"));
        if (isValidInput(header, description, deadline)) {
            task.setHeader(header);
            task.setDescription(description);
            task.setIdUser(idUser);
            task.setDeadline(deadline);
            taskRepo.save(task);
        } else {
            throw new InputMismatchException("Incorrect input values");
        }
        return "Task: " + idTask + " edited";
    }

    public String cleanAllTaskTracker() {
        userRepo.deleteAll();
        taskRepo.deleteAll();
        return "All users and task deleted";
    }

    public void stopApplication() {
        System.exit(0);
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
