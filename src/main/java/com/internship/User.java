package com.internship;

import java.util.List;

public class User {
    private int id;
    private String userName;
    private List<Task> tasks;

    public User(int id, String userName, List<Task> tasks) {
        this.id = id;
        this.userName = userName;
        this.tasks = tasks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}
