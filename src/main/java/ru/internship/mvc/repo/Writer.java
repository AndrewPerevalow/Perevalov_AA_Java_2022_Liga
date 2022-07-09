package ru.internship.mvc.repo;

import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.User;

import java.util.List;
import java.util.Map;

public interface Writer {
    void writeListTasks (String fileName, List<Task> taskList, Map<Integer, User> usersMap);
    void writeUsers (String fileName, Map<Integer, User> usersMap);
}
