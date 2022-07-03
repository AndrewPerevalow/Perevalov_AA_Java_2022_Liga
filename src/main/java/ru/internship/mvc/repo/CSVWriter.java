package ru.internship.mvc.repo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.User;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class CSVWriter implements Writer {
    private static final String CSV_SEPARATOR = ", ";

    private static String DEFAULT_STATUS;

    @Value("${statuses.default-status}")
    public void setDefaultStatus(String status) {
        CSVWriter.DEFAULT_STATUS = status;
    }

    @Override
    public void writeListTasks (String fileName, List<Task> taskList, Map<Integer, User> usersMap) throws InputMismatchException {
        File csvFile = new File("src/main/resources/" + fileName);
        try (BufferedWriter csvWriter = new BufferedWriter(new FileWriter(csvFile))) {
            for (Task task : taskList) {
                StringBuilder sb = new StringBuilder();
                sb.append(task.getId());
                sb.append(CSV_SEPARATOR);
                sb.append(task.getHeader());
                sb.append(CSV_SEPARATOR);
                sb.append(task.getDescription());
                sb.append(CSV_SEPARATOR);
                sb.append(task.getIdUser());
                sb.append(CSV_SEPARATOR);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                sb.append(dateFormat.format(task.getDeadline()));
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

    @Override
    public void writeUsers (String fileName, Map<Integer, User> usersMap) throws InputMismatchException {
        File csvFile = new File("src/main/resources/" + fileName);
        try (BufferedWriter csvWriter = new BufferedWriter(new FileWriter(csvFile))) {
            for (User user : usersMap.values()) {
                StringBuilder sb = new StringBuilder();
                sb.append(user.getId());
                sb.append(CSV_SEPARATOR);
                sb.append(user.getUserName());
                sb.append("\n");
                csvWriter.write(sb.toString());
            }
            csvWriter.flush();
        } catch (IOException exception) {
            System.err.println("Failed file write" + exception.getMessage());
        }
    }
}
