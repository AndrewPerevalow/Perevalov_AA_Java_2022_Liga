package ru.internship.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.internship.mvc.dto.input.InputTaskDto;
import ru.internship.mvc.model.Project;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.User;
import ru.internship.mvc.model.enums.Status;
import ru.internship.mvc.repo.ProjectRepo;
import ru.internship.mvc.repo.TaskRepo;
import ru.internship.mvc.repo.UserRepo;
import ru.internship.mvc.util.TaskMapper;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.InputMismatchException;


@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final ProjectRepo projectRepo;

    public Task addNewTask(Long idUser, Long idProject, InputTaskDto inputTask) {
        User user = userRepo.findById(idUser)
                .orElseThrow(() -> new EntityNotFoundException("User with this id doesn't exist"));
        Project project = projectRepo.findById(idProject)
                .orElseThrow(() -> new EntityNotFoundException("Project with this id doesn't exist"));
        if (isDateValid(inputTask.getDeadline())) {
            Task task = new Task();
            TaskMapper.DtoToEntity(task, inputTask);
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
        if (validStatus(newStatus)) {
            task.setStatus(newStatus);
            return taskRepo.save(task);
        } else {
            throw new InputMismatchException("Incorrect status");
        }
    }

    public Task editTask(Long id, Long idUser, Long idProject, InputTaskDto updatedTask) {
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with this id doesn't exist"));
        User user = userRepo.findById(idUser)
                .orElseThrow(() -> new EntityNotFoundException("User with this id doesn't exist"));
        Project project = projectRepo.findById(idProject)
                .orElseThrow(() -> new EntityNotFoundException("Task with this id doesn't exist"));
        if (isDateValid(updatedTask.getDeadline())) {
            TaskMapper.DtoToEntity(task, updatedTask);
            task.setUser(user);
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

    private boolean validStatus(String status) {
        return status.equals(Status.DEFAULT_STATUS.getStatus()) ||
                status.equals(Status.WORK_STATUS.getStatus()) ||
                status.equals(Status.COMPLETE_STATUS.getStatus());
    }
}
