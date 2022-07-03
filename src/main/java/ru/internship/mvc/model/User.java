package ru.internship.mvc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class User {
    private int id;
    private String userName;
    private List<Task> tasks;
}
