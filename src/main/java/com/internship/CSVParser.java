package com.internship;

import com.internship.model.Task;
import com.internship.model.User;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CSVParser implements Parser {
    private final static String DEFAULT_STATUS = "Новое";

    @Override
    public List<Task> getListTasks(String fileName) {
        List<Task> tasksList = new ArrayList<>();
        String row;
        String[] data;
        File csvFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).getFile());
        try (BufferedReader csvReader = new BufferedReader(new FileReader(csvFile))) {
            while ((row = csvReader.readLine()) != null) {
                data = row.split(", ");
                tasksList.add(createTask(data));
            }
        } catch (FileNotFoundException exception) {
            System.err.println("File not found");
        } catch (IOException exception) {
            System.err.println("Failed file read" + exception.getMessage());
        }
        return tasksList;
    }

    private Task createTask(String[] data) {
        Task task = null;
        try {
            task = new Task(Integer.parseInt(data[0]), data[1], data[2], Integer.parseInt(data[3]), new SimpleDateFormat("dd.MM.yyyy").parse(data[4]), DEFAULT_STATUS);
        } catch (ParseException exception) {
            System.err.println("Parse fail" + exception.getMessage());
        }
        return task;
    }

    @Override
    public Map<Integer, User> getMapUsers(String fileName) {
        Map<Integer, User> usersMap = new HashMap<>();
        String row;
        String[] data;
        File csvFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).getFile());
        try (BufferedReader csvReader = new BufferedReader(new FileReader(csvFile))) {
            while ((row = csvReader.readLine()) != null) {
                data = row.split(", ");
                usersMap.put(Integer.parseInt(data[0]), createUser(data));
            }
        } catch (FileNotFoundException exception) {
            System.err.println("File not found");
        } catch (IOException exception) {
            System.err.println("Failed file read" + exception.getMessage());
        }
        return usersMap;
    }

    private User createUser(String[] data) {
        return new User(Integer.parseInt(data[0]), data[1], new ArrayList<>());
    }
}
