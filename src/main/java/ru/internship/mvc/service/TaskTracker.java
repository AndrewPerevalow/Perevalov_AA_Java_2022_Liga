package ru.internship.mvc.service;

import java.util.Date;

public interface TaskTracker {
    void stopApplication();
    String changeTaskStatus(int idTask, String newStatus);
    String addNewTask(String header, String description, int idUser, Date deadline);
    String removeTask(int idTask);
    String editTask(int idTask, String header, String description, int idUser, Date deadline);
    String cleanAllTaskTracker();
}
