package com.internship;

public interface TaskTracker {
    void getAllTasksForUsers();
    void printAllTasksForUsers(int idUser);
    void filterAllTasksForUsersByStatus(int idUser, String status);
    void changeTaskStatus(int idTask, String newStatus);
    int getCountUsers();
}
