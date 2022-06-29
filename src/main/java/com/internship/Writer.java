package com.internship;

import com.internship.model.Task;
import com.internship.model.User;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

public interface Writer {
    void writeListTasks (String fileName, List<Task> taskList, Map<Integer, User> usersMap) throws InputMismatchException;
    void writeUsers (String fileName, Map<Integer, User> usersMap) throws InputMismatchException;
}
