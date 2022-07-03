package ru.internship.mvc.repo;

import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.User;

import java.util.List;
import java.util.Map;

public interface Reader {
    List<Task> getListTasks(String fileName);
    Map<Integer, User> getMapUsers(String fileName);
}
