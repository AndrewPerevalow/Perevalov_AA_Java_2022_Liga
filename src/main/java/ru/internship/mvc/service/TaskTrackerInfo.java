package ru.internship.mvc.service;

public interface TaskTrackerInfo {
    String printAllTasksForUsers(int idUser);
    String filterAllTasksForUsersByStatus(int idUser, String status);
}
