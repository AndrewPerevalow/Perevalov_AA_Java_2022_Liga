package com.internship;

import com.internship.model.Task;
import com.internship.model.User;

import java.util.List;
import java.util.Map;

public interface Reader {
    List<Task> getListTasks(String fileName);
    Map<Integer, User> getMapUsers(String fileName);
}
