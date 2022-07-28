package ru.internship.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.Task_;
import ru.internship.mvc.model.User;
import ru.internship.mvc.model.enums.Status;
import ru.internship.mvc.repo.TaskRepo;
import ru.internship.mvc.repo.UserRepo;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.InputMismatchException;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskInfoService {

    @PersistenceContext
    EntityManager entityManager;

    private final UserRepo userRepo;
    private final TaskRepo taskRepo;

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
        if (taskList.size() != 0) {
            return taskList;
        } else {
            throw new InputMismatchException("This user doesn't have any tasks");
        }
    }

    public List<Task> filterAllTasksForUsersByStatus(Long id, String status) {
        userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with this id doesn't exist"));
        List<Task> taskList;
        if (status.equals(Status.DEFAULT_STATUS.getStatus()) ||
                status.equals(Status.WORK_STATUS.getStatus()) ||
                status.equals(Status.COMPLETE_STATUS.getStatus())) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Task> query = criteriaBuilder.createQuery(Task.class);
            Root<Task> root = query.from(Task.class);
            query.select(root);
            query.where(criteriaBuilder.and(
                    criteriaBuilder.equal(root.get(Task_.user), id),
                    criteriaBuilder.equal(root.get(Task_.status), status)));
            TypedQuery<Task> typedQuery = entityManager.createQuery(query);
            taskList = typedQuery.getResultList();
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
