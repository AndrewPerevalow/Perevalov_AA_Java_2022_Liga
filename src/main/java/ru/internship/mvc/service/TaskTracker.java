package ru.internship.mvc.service;

public interface TaskTracker {
    void stopApplication();
    void changeTaskStatus(int idTask, String newStatus);
    void addNewTask(String header, String description, int idUser, String deadline);
    void removeTask(int idTask);
    void editTask(int idTask, String header, String description, int idUser, String deadline);
    void cleanAllTaskTracker();
}
