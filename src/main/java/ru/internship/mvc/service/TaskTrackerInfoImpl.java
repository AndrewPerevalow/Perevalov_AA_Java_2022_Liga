package ru.internship.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.User;

import javax.annotation.PostConstruct;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

@Service
public class TaskTrackerInfoImpl implements TaskTrackerInfo {

    private static String DEFAULT_STATUS;

    @Value("${statuses.default-status}")
    public void setDefaultStatus(String status) {
        TaskTrackerInfoImpl.DEFAULT_STATUS = status;
    }

    private static String WORK_STATUS;

    @Value("${statuses.work-status}")
    public void setWorkStatus(String status) {
        TaskTrackerInfoImpl.WORK_STATUS = status;
    }

    private static String COMPLETE_STATUS;

    @Value("${statuses.complete-status}")
    public void setCompleteStatus(String status) {
        TaskTrackerInfoImpl.COMPLETE_STATUS = status;
    }

    private Map<Integer, User> usersMap;
    private List<Task> tasksList;

    private final Initialize init;

    @Autowired
    public TaskTrackerInfoImpl(Initialize init) {
        this.init = init;
    }

    @PostConstruct
    public void firstInit() {
        tasksList = init.getTasksList();
        usersMap = init.getUsersMap();
    }

    public Map<Integer, User> getUsersMap() {
        return usersMap;
    }

    public List<Task> getTasksList() {
        return tasksList;
    }

    public void setUsersMap(Map<Integer, User> usersMap) {
        this.usersMap = usersMap;
    }

    public void setTasksList(List<Task> tasksList) {
        this.tasksList = tasksList;
    }

    @Override
    public String printAllTasksForUsers(int idUser) {
        if (usersMap.containsKey(idUser)) {
            List<Task> listTasks = usersMap.get(idUser).getTasks();
            if (listTasks.size() != 0) {
                return listTasks.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\, ", "");
            } else {
                throw new InputMismatchException("This user doesn't have any tasks");
            }
        } else {
            throw new InputMismatchException("User with this id doesn't exist");
        }
    }

    @Override
    public String filterAllTasksForUsersByStatus(int idUser, String status) {
        if (usersMap.containsKey(idUser)) {
            List<Task> listTasks;
            if (status.equals(DEFAULT_STATUS) || status.equals(WORK_STATUS) || status.equals(COMPLETE_STATUS)) {
                listTasks = usersMap.get(idUser).getTasks().stream()
                        .filter(a -> a.getStatus().equals(status)).toList();
            } else {
                throw new InputMismatchException("Incorrect status");
            }
            if (listTasks.size() != 0) {
                return listTasks.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\, ", "");

            } else {
                throw new InputMismatchException("This user doesn't have tasks with this status");
            }
        } else {
            throw new InputMismatchException("User with this id doesn't exist");
        }
    }
}
