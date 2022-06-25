package com.internship.model;

import java.util.Date;

public class Task {
    private int id;
    private String header;
    private String description;
    private int idUser;
    private Date deadline;
    private String status;

    public Task(int id, String header, String description,int idUser, Date deadline, String status) {
        this.id = id;
        this.header = header;
        this.description = description;
        this.idUser = idUser;
        this.deadline = deadline;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Задание: " +
                "id = " + id +
                "; Заголовок = '" + header + '\'' +
                "; Описание = '" + description + '\'' +
                "; Срок выполения = " + deadline +
                "; Статус = '" + status + '\'' + '.' + '\n';
    }
}
