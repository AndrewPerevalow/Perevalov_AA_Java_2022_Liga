package com.internship;

import java.util.Date;

public interface TaskTracker {
    void getAllTasksForUsers();
    void printAllTasksForUsers(int idUser);
    void filterAllTasksForUsersByStatus(int idUser, String status);
    void changeTaskStatus(int idTask, String newStatus);
    void addNewTask(String header, String description, int idUser, String deadline);
    void removeTask(int idTask);
    void addNewUser(String userName);
    void removeUser(int idUser);
    void editTask(int idTask, String header, String description, int idUser, String deadline);
    void cleanAllTaskTracker();
}
