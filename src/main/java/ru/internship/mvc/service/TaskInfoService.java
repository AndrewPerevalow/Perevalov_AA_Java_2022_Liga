package ru.internship.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.User;
import ru.internship.mvc.model.enums.Status;
import ru.internship.mvc.repo.TaskRepo;
import ru.internship.mvc.repo.UserRepo;
import ru.internship.mvc.service.specifications.FilterAllTasksByStatus;

import javax.persistence.EntityNotFoundException;
import java.util.InputMismatchException;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskInfoService {

    private final UserRepo userRepo;
    private final TaskRepo taskRepo;
    private final FilterAllTasksByStatus filterAllTasksByStatus;

    public Task getOne(Long id) {
       return taskRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with this id doesn't exist"));
    }

    public List<Task> getAll() {
        return taskRepo.findAll();
    }

    public List<Task> printAllTasksForUsers(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with this id doesn't exist"));
        List<Task> taskList = user.getTasks();
        if (!taskList.isEmpty()) {
            return taskList;
        } else {
            throw new InputMismatchException("This user doesn't have any tasks");
        }
    }

    public List<Task> filterAllTasksForUsersByStatus(Long id, String status) {
        userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with this id doesn't exist"));
        List<Task> taskList;
        if (validStatus(status)) {
            taskList = filterAllTasksByStatus.filterAllTasksForUsersByStatus(id, status);
        } else {
            throw new InputMismatchException("Incorrect status");
        }
        if (!taskList.isEmpty()) {
            return taskList;
        } else {
            throw new InputMismatchException("This user doesn't have tasks with this status");
        }
    }

    private boolean validStatus(String status) {
        return status.equals(Status.DEFAULT_STATUS.getStatus()) ||
                status.equals(Status.WORK_STATUS.getStatus()) ||
                status.equals(Status.COMPLETE_STATUS.getStatus());
    }
}
