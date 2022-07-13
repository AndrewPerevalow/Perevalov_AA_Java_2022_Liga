package ru.internship.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.User;
import ru.internship.mvc.repo.Writer;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;


@Service
public class TaskTrackerImpl implements TaskTracker {

    private static String DEFAULT_STATUS;

    @Value("${statuses.default-status}")
    public void setDefaultStatus(String status) {
        TaskTrackerImpl.DEFAULT_STATUS = status;
    }

    private static String WORK_STATUS;

    @Value("${statuses.work-status}")
    public void setWorkStatus(String status) {
        TaskTrackerImpl.WORK_STATUS = status;
    }

    private static String COMPLETE_STATUS;

    @Value("${statuses.complete-status}")
    public void setCompleteStatus(String status) {
        TaskTrackerImpl.COMPLETE_STATUS = status;
    }

    @Value("${files.user-file}")
    private String userFile;
    @Value("${files.task-file}")
    private String taskFile;

    private final Writer csvWriter;
    private final Initialize init;

    private Map<Integer, User> usersMap;
    private List<Task> tasksList;

    @Autowired
    TaskTrackerImpl(Initialize init, Writer csvWriter) {
        this.init = init;
        this.csvWriter = csvWriter;
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
    public void stopApplication() {
        System.exit(0);
    }

    @Override
    public String changeTaskStatus(int idTask, String newStatus) {
        if (newStatus.equals(DEFAULT_STATUS) || newStatus.equals(WORK_STATUS) || newStatus.equals(COMPLETE_STATUS)) {
            for (Task task : tasksList) {
                if (task.getId() == idTask) {
                    task.setStatus(newStatus);
                    return "Status task id = "+ idTask + " changed to: " + newStatus;
                }
            }
            throw new InputMismatchException("Task with this id doesn't exist");
        } else {
            throw new InputMismatchException("Incorrect status");
        }
    }

    @Override
    public String addNewTask(String header, String description, int idUser, Date deadline) {
        if (usersMap.containsKey(idUser)) {
            if (isValidInput(header, description, deadline)) {
                Task task = new Task(tasksList.size() + 1, header, description, idUser, deadline, DEFAULT_STATUS);
                tasksList.add(task);
                usersMap.get(task.getIdUser()).getTasks().add(task);
                csvWriter.writeListTasks(taskFile, tasksList, usersMap);
                return "New task added";
            } else {
                throw new InputMismatchException("Incorrect input values");
            }
        } else {
            throw new InputMismatchException("User with this id doesn't exist");
        }
    }

    @Override
    public String removeTask(int idTask) {
        if (tasksList.stream().anyMatch(task -> task.getId() == idTask)) {
            tasksList.removeIf(task -> task.getId() == idTask);
            for (User user : usersMap.values()) {
                user.getTasks().removeIf(task -> task.getId() == idTask);
            }
            csvWriter.writeListTasks(taskFile, tasksList, usersMap);
            return "Task deleted";
        } else {
            throw new InputMismatchException("Task with this id doesn't exist");
        }
    }

    @Override
    public String editTask(int idTask, String header, String description, int idUser, Date deadline) {
        if (usersMap.containsKey(idUser)) {
            if (isValidInput(header, description, deadline)) {
                Task task = tasksList.stream().filter(t -> t.getId() == idTask).findFirst().orElseThrow(() -> new InputMismatchException("Task with this id doesn't exist"));
                task.setHeader(header);
                task.setDescription(description);
                task.setIdUser(idUser);
                task.setDeadline(deadline);
                if (!(usersMap.get(task.getIdUser()).getTasks().contains(task))) {
                    usersMap.get(task.getIdUser()).getTasks().add(task);
                }
                csvWriter.writeListTasks(taskFile, tasksList, usersMap);
                return "Task edited";
            } else {
                throw new InputMismatchException("Incorrect input values");
            }
        } else {
            throw new InputMismatchException("User with this id doesn't exist");
        }
    }

    @Override
    public String cleanAllTaskTracker() {
        tasksList.clear();
        usersMap.clear();
        csvWriter.writeListTasks(taskFile, tasksList, usersMap);
        csvWriter.writeUsers(userFile, usersMap);
        return "All users and task deleted";
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
