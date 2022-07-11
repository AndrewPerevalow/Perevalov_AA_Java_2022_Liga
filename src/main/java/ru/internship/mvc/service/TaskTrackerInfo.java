package ru.internship.mvc.service;

public interface TaskTrackerInfo {
    void printAllTasksForUsers(int idUser);
    void filterAllTasksForUsersByStatus(int idUser, String status);
}
