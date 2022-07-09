package ru.internship.mvc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class Task {
    private int id;
    private String header;
    private String description;
    private int idUser;
    private Date deadline;
    private String status;

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
