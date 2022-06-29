package com.internship;

import com.internship.model.Task;
import com.internship.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class TaskTrackerImpl implements TaskTracker {
    private final static String DEFAULT_STATUS = "Новое";
    private final static String WORK_STATUS = "В работе";
    private final static String COMPLETE_STATUS = "Готово";
    private final static String USERS_FILE_NAME = "users.csv";
    private final static String TASKS_FILE_NAME = "tasks.csv";
    private final Reader csvReader = new CSVReader();
    private final Writer csvWriter = new CSVWriter();
    private final Map<Integer, User> usersMap = csvReader.getMapUsers(USERS_FILE_NAME);
    private final List<Task> tasksList = csvReader.getListTasks(TASKS_FILE_NAME);


    @Override
    public void getAllTasksForUsers() {
        for (Task task : tasksList) {
            if (usersMap.containsKey(task.getIdUser())) {
                usersMap.get(task.getIdUser()).getTasks().add(task);
            }
        }
    }

    @Override
    public void printAllTasksForUsers(int idUser) {
        if (!(usersMap.containsKey(idUser))) {
            System.err.println("User with this id doesn't exists");
            return;
        }

        List<Task> listTasks = usersMap.get(idUser).getTasks();
        if (listTasks.size() != 0) {
            System.out.println(listTasks.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\, ", ""));
        } else {
            System.out.println("This user doesn't have any tasks");
        }
    }

    @Override
    public void filterAllTasksForUsersByStatus(int idUser, String status) {
        if (!(usersMap.containsKey(idUser))) {
            System.err.println("User with this id doesn't exists");
            return;
        }

        List<Task> listTasks;
        if (status.equals(DEFAULT_STATUS) || status.equals(WORK_STATUS) || status.equals(COMPLETE_STATUS)) {
            listTasks = usersMap.get(idUser).getTasks().stream()
                    .filter(a -> a.getStatus().equals(status)).toList();
        } else {
            System.err.println("Incorrect input values");
            return;
        }
        if (listTasks.size() != 0) {
            System.out.println(listTasks.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\, ", ""));

        } else {
            System.out.println("This user doesn't have tasks with this status");
        }
    }

    @Override
    public void changeTaskStatus(int idTask, String newStatus) {
        if (newStatus.equals(DEFAULT_STATUS) || newStatus.equals(WORK_STATUS) || newStatus.equals(COMPLETE_STATUS)) {
            for (Task task : tasksList) {
                if (task.getId() == idTask) {
                    task.setStatus(newStatus);
                    System.out.println("Status task id = "+ idTask + " changed to: " + newStatus);
                    return;
                }
            }
            System.err.println("Task with this id doesn't exists");
        } else {
            System.err.println("Incorrect input values");
        }
    }

    @Override
    public void addNewTask(String header, String description, int idUser, String deadline) {
        if (!(usersMap.containsKey(idUser))) {
            System.err.println("User with this id doesn't exists");
            return;
        }
        try {
            Task task = new Task(tasksList.size() + 1, header, description, idUser, new SimpleDateFormat("dd.MM.yyyy").parse(deadline), DEFAULT_STATUS);
            tasksList.add(task);
            usersMap.get(task.getIdUser()).getTasks().add(task);
            csvWriter.writeListTasks(TASKS_FILE_NAME, tasksList, usersMap);
            System.out.println("New task added");
        } catch (ParseException exception) {
            System.err.println("Parse fail" + exception.getMessage());
        } catch (InputMismatchException exception) {
            System.err.println("Incorrect input values");
        }
    }

    @Override
    public void removeTask(int idTask) {
        tasksList.removeIf(task -> task.getId() == idTask);
        for (User user : usersMap.values()) {
            user.getTasks().removeIf(task -> task.getId() == idTask);
        }
        csvWriter.writeListTasks(TASKS_FILE_NAME, tasksList, usersMap);
        System.out.println("Task deleted");
    }

    @Override
    public void addNewUser(String userName) {
        try {
            usersMap.put(usersMap.size() + 1, new User(usersMap.size() + 1, userName, new ArrayList<>()));
            csvWriter.writeUsers(USERS_FILE_NAME, usersMap);
            System.out.println("New user added");
        } catch (InputMismatchException exception) {
            System.err.println("Incorrect input values");
        }
    }

    @Override
    public void removeUser(int idUser) {
        usersMap.remove(idUser);
        csvWriter.writeUsers(USERS_FILE_NAME, usersMap);
        System.out.println("User deleted");
    }

    @Override
    public void editTask(int idTask, String header, String description, int idUser, String deadline) {
        try {
            Task task = tasksList.stream().filter(t -> t.getId() == idTask).findFirst().orElseThrow();
            task.setHeader(header);
            task.setDescription(description);
            task.setIdUser(idUser);
            task.setDeadline(new SimpleDateFormat("dd.MM.yyyy").parse(deadline));
            if (!(usersMap.get(task.getIdUser()).getTasks().contains(task))) {
                usersMap.get(task.getIdUser()).getTasks().add(task);
            }
            csvWriter.writeListTasks(TASKS_FILE_NAME, tasksList, usersMap);
            System.out.println("Task edited");
        } catch (NoSuchElementException exception) {
            System.err.println("Incorrect input values");
        } catch (ParseException exception) {
            System.err.println("Parse fail" + exception.getMessage());
        }
    }

    @Override
    public void cleanAllTaskTracker() {
        tasksList.clear();
        usersMap.clear();
        csvWriter.writeListTasks(TASKS_FILE_NAME, tasksList, usersMap);
        csvWriter.writeUsers(USERS_FILE_NAME, usersMap);
        System.out.println("All users and task deleted");
    }
}
