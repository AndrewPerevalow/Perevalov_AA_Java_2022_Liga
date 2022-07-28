package ru.internship.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.internship.mvc.model.Project;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.User;
import ru.internship.mvc.model.enums.Status;
import ru.internship.mvc.repo.ProjectRepo;
import ru.internship.mvc.repo.TaskRepo;
import ru.internship.mvc.repo.UserRepo;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.InputMismatchException;


@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final ProjectRepo projectRepo;

    public Task addNewTask(Long idUser, Long idProject, Task task) {
        User user = userRepo.findById(idUser)
                .orElseThrow(() -> new EntityNotFoundException("User with this id doesn't exist"));
        Project project = projectRepo.findById(idProject)
                .orElseThrow(() -> new EntityNotFoundException("Project with this id doesn't exist"));
        if (isDateValid(task.getDeadline())) {
            task.setUser(user);
            task.setProject(project);
            return taskRepo.save(task);
        } else {
            throw new InputMismatchException("Incorrect input date");
        }
    }

    public String removeTask(Long idTask) {
        taskRepo.findById(idTask)
                .orElseThrow(() -> new EntityNotFoundException("Task with this id doesn't exist"));
        taskRepo.deleteById(idTask);
        return "Task: " + idTask + " deleted";
    }

    public Task changeTaskStatus(Long idTask, String newStatus) {
        Task task = taskRepo.findById(idTask)
                .orElseThrow(() -> new EntityNotFoundException("Task with this id doesn't exist"));
        if (newStatus.equals(Status.DEFAULT_STATUS.getStatus()) ||
                newStatus.equals(Status.WORK_STATUS.getStatus()) ||
                newStatus.equals(Status.COMPLETE_STATUS.getStatus())) {
            task.setStatus(newStatus);
            return taskRepo.save(task);
        } else {
            throw new InputMismatchException("Incorrect status");
        }
    }

    public Task editTask(Long id, Long idUser, Long idProject, Task updatedTask) {
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with this id doesn't exist"));
        User user = userRepo.findById(idUser)
                .orElseThrow(() -> new EntityNotFoundException("User with this id doesn't exist"));
        Project project = projectRepo.findById(idProject)
                .orElseThrow(() -> new EntityNotFoundException("Task with this id doesn't exist"));
        if (isDateValid(updatedTask.getDeadline())) {
            task.setHeader(updatedTask.getHeader());
            task.setDescription(updatedTask.getDescription());
            task.setUser(user);
            task.setDeadline(updatedTask.getDeadline());
            task.setProject(project);
            return taskRepo.save(task);
        } else {
            throw new InputMismatchException("Incorrect input date");
        }
    }

    public String cleanAllTaskTracker() {
        userRepo.deleteAll();
        taskRepo.deleteAll();
        return "All users and task deleted";
    }

    public void stopApplication() {
        System.exit(0);
    }

    private boolean isDateValid(Date inputDate) {
        Date currentDate = new Date();
        int result = inputDate.compareTo(currentDate);
        return result >= 0;
    }
}
