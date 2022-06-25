package com.internship;

import com.internship.model.Task;
import com.internship.model.User;

import java.util.List;
import java.util.Map;

public class TaskTrackerImpl implements TaskTracker {
    private final static String USERS_FILE_NAME = "users.csv";
    private final static String TASKS_FILE_NAME = "tasks.csv";
    private Parser csvParser = new CSVParser();
    private Map<Integer, User> usersMap = csvParser.getMapUsers(USERS_FILE_NAME);
    private List<Task> tasksList = csvParser.getListTasks(TASKS_FILE_NAME);

    @Override
    public void getAllTasksForUsers() {
        for (Task task : tasksList) {
            if (usersMap.containsKey(task.getIdUser())) {
                usersMap.get(task.getIdUser()).getTasks().add(task);
            }
        }
    }

    @Override
    public void printAllTasksForUsers(int idUser) {
        System.out.println(usersMap.get(idUser).getTasks()
                .toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\, ", ""));
    }

    @Override
    public void filterAllTasksForUsersByStatus(int idUser, String status) {
        System.out.println(usersMap.get(idUser).getTasks().stream()
                .filter(a -> a.getStatus().equals(status)).toList()
                .toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\, ", ""));
    }

    @Override
    public void changeTaskStatus(int idTask, String newStatus) {
        for (Task task : tasksList) {
            if (task.getId() == idTask) {
                task.setStatus(newStatus);
            }
        }
    }

    @Override
    public int getCountUsers() {
        return usersMap.size();
    }
}
