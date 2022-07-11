package ru.internship.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.User;
import ru.internship.mvc.repo.Reader;

import java.util.List;
import java.util.Map;

@Service
public class Initialize {

    @Value("${files.user-file}")
    private String userFile;
    @Value("${files.task-file}")
    private String taskFile;

    private final Reader csvReader;

    private Map<Integer, User> usersMap;
    private List<Task> tasksList;

    @Autowired
    public Initialize(Reader csvReader) {
        this.csvReader = csvReader;
    }

    public Map<Integer, User> getUsersMap() {
        usersMap = csvReader.getMapUsers(userFile);
        getAllTasksForUsers();
        return usersMap;
    }

    public List<Task> getTasksList() {
        return tasksList = csvReader.getListTasks(taskFile);
    }

    public void setUsersMap(Map<Integer, User> usersMap) {
        this.usersMap = usersMap;
    }

    public void setTasksList(List<Task> tasksList) {
        this.tasksList = tasksList;
    }

    private void getAllTasksForUsers() {
        for (Task task : tasksList) {
            if (usersMap.containsKey(task.getIdUser())) {
                usersMap.get(task.getIdUser()).getTasks().add(task);
            }
        }
    }

}
