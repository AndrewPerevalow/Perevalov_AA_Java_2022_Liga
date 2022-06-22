package com.internship;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Parser {
    void readFile(String fileName);
    void getAllTasksForUsers();
    void printAllTasksForUsers(int idUser);
    void filterAllTasksForUsersByStatus(int idUser, String status);
    void changeTaskStatus(int idTask, String newStatus);
    Map<Integer, User> getUsersMap();
}
