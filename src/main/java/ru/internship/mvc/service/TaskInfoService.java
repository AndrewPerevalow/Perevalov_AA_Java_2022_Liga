package ru.internship.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.User;
import ru.internship.mvc.repo.UserRepo;

import javax.persistence.EntityNotFoundException;
import java.util.InputMismatchException;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class TaskInfoService {

    private static String DEFAULT_STATUS;

    @Value("${statuses.default-status}")
    public void setDefaultStatus(String status) {
        TaskInfoService.DEFAULT_STATUS = status;
    }

    private static String WORK_STATUS;

    @Value("${statuses.work-status}")
    public void setWorkStatus(String status) {
        TaskInfoService.WORK_STATUS = status;
    }

    private static String COMPLETE_STATUS;

    @Value("${statuses.complete-status}")
    public void setCompleteStatus(String status) {
        TaskInfoService.COMPLETE_STATUS = status;
    }

    private final UserRepo userRepo;

    @Autowired
    public TaskInfoService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<Task> printAllTasksForUsers(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with this id doesn't exist"));
        List<Task> taskList = user.getTasks();
        if (taskList.size() != 0) {
            return taskList;
        } else {
            throw new InputMismatchException("This user doesn't have any tasks");
        }
    }

    public List<Task> filterAllTasksForUsersByStatus(Long id, String status) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with this id doesn't exist"));
        List<Task> taskList;
        if (status.equals(DEFAULT_STATUS) || status.equals(WORK_STATUS) || status.equals(COMPLETE_STATUS)) {
            taskList = user.getTasks().stream()
                    .filter(a -> a.getStatus().equals(status)).toList();
        } else {
            throw new InputMismatchException("Incorrect status");
        }
        if (taskList.size() != 0) {
            return taskList;
        } else {
            throw new InputMismatchException("This user doesn't have tasks with this status");
        }
    }
}
