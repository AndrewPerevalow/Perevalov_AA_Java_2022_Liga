package com.internship;

import com.internship.model.Task;
import com.internship.model.User;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CSVWriter implements Writer {
    private static final String CSV_SEPARATOR = ", ";
    private final static String DEFAULT_STATUS = "Новое";

    @Override
    public void writeListTasks (String fileName, List<Task> taskList, Map<Integer, User> usersMap) throws InputMismatchException {
        File csvFile = new File("src/main/resources/" + fileName);
        try (BufferedWriter csvWriter = new BufferedWriter(new FileWriter(csvFile))) {
            for (Task task : taskList) {
                StringBuilder sb = new StringBuilder();
                sb.append(task.getId());
                sb.append(CSV_SEPARATOR);
                if (task.getHeader().trim().length() != 0) {
                    sb.append(task.getHeader());
                } else {
                    throw new InputMismatchException();
                }
                sb.append(CSV_SEPARATOR);
                if (task.getDescription().trim().length() != 0) {
                    sb.append(task.getDescription());
                } else {
                    throw new InputMismatchException();
                }
                sb.append(CSV_SEPARATOR);
                if (usersMap.containsKey(task.getIdUser())) {
                    sb.append(task.getIdUser());
                } else {
                    throw new InputMismatchException();
                }
                sb.append(CSV_SEPARATOR);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                if (isDateValid(task.getDeadline())) {
                    sb.append(dateFormat.format(task.getDeadline()));
                } else {
                    throw new InputMismatchException();
                }
                if (!(task.getStatus().equals(DEFAULT_STATUS))) {
                    sb.append(CSV_SEPARATOR);
                    sb.append(task.getStatus());
                }
                sb.append("\n");
                csvWriter.write(sb.toString());
            }
            csvWriter.flush();
        } catch (IOException exception) {
            System.err.println("Failed file write" + exception.getMessage());
        }
    }

    private boolean isDateValid(Date inputDate) {
        if (inputDate == null) {
            return false;
        }
        Date currentDate = new Date();
        int result = inputDate.compareTo(currentDate);
        return result >= 0;
    }

    @Override
    public void writeUsers (String fileName, Map<Integer, User> usersMap) throws InputMismatchException {
        File csvFile = new File("src/main/resources/" + fileName);
        try (BufferedWriter csvWriter = new BufferedWriter(new FileWriter(csvFile))) {
            for (User user : usersMap.values()) {
                StringBuilder sb = new StringBuilder();
                sb.append(user.getId());
                sb.append(CSV_SEPARATOR);
                if (user.getUserName().trim().length() != 0) {
                    sb.append(user.getUserName());
                } else {
                    throw new InputMismatchException();
                }
                sb.append("\n");
                csvWriter.write(sb.toString());
            }
            csvWriter.flush();
        } catch (IOException exception) {
            System.err.println("Failed file write" + exception.getMessage());
        }
    }
}
