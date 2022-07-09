package ru.internship.mvc.service;

public interface TaskTracker {
    void stopApplication();
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
