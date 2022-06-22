package com.internship;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CSVParser implements Parser {
    private final static String DEFAULT_STATUS = "Новое";
    private final static String WORK_STATUS = "В работе";
    private final static String COMPLETE_STATUS = "Готово";
    private final static String TASKS_FILE_NAME = "tasks.csv";
    private final static String USERS_FILE_NAME = "users.csv";
    private List<Task> tasksList = new ArrayList<>();
    private Map<Integer, User> usersMap = new HashMap<>();

    @Override
    public Map<Integer, User> getUsersMap() {
        return usersMap;
    }

    @Override
    public void readFile(String fileName) {
        String row;
        String[] data;
        File csvFile = new File(getClass().getClassLoader().getResource(fileName).getFile());
        try (BufferedReader csvReader = new BufferedReader(new FileReader(csvFile))) {
            while ((row = csvReader.readLine()) != null) {
                data = row.split(", ");
                if (fileName.equals(TASKS_FILE_NAME)) {
                    initializeTasks(data);
                } else if (fileName.equals(USERS_FILE_NAME)) {
                    initializeUsers(data);
                }
            }
        } catch (FileNotFoundException exception) {
            System.err.println("File not found");
        } catch (IOException exception) {
            System.err.println("Failed file read" + exception.getMessage());
        }
    }


    private void initializeTasks(String[] data) {
        Task task = null;
        try {
            task = new Task(Integer.parseInt(data[0]), data[1], data[2], Integer.parseInt(data[3]), new SimpleDateFormat("dd.MM.yyyy").parse(data[4]), DEFAULT_STATUS);
        } catch (ParseException exception) {
            System.err.println("Parse fail" + exception.getMessage());
        }
        tasksList.add(task);
    }


    private void initializeUsers(String[] data) {
        User user = new User(Integer.parseInt(data[0]), data[1], new ArrayList<>());
        usersMap.put(Integer.parseInt(data[0]), user);
    }

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
        System.out.println(usersMap.get(idUser).getTasks()
                    .toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\, ", ""));
    }

    @Override
    public void filterAllTasksForUsersByStatus(int idUser, String status) {
        System.out.println(usersMap.get(idUser).getTasks().stream()
                    .filter(a -> a.getStatus().equals(status)).toList()
                    .toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\, ", ""));
    }

    @Override
    public void changeTaskStatus(int idTask, String newStatus) {
        for (Task task : tasksList) {
            if (task.getId() == idTask) {
                task.setStatus(newStatus);
            }
        }
    }

    public static void main(String[] args) {
        Parser parser = new CSVParser();
        parser.readFile(TASKS_FILE_NAME);
        parser.readFile(USERS_FILE_NAME);
        parser.getAllTasksForUsers();
        Scanner sc = new Scanner(System.in);
        String command;
        while (true) {
            try {
                sc.nextLine();
                System.out.println("Input your command...");
                System.out.println("All commands: STOP_APP, PRINT_ALL_USER_TASKS, CHANGE_TASKS_STATUS");
                command = sc.nextLine();
                switch (command) {
                    case "STOP_APP" -> System.exit(0);


                    case "PRINT_ALL_USER_TASKS" -> {
                        System.out.println("Choose the way you want to print tasks: WITHOUT_FILTER or WITH_FILTER ?");
                        String printCommand = sc.nextLine();
                        System.out.println("Input user id you want to print: ");
                        int idUser = sc.nextInt();
                        if (idUser < 1 || idUser > parser.getUsersMap().size()) {
                            throw new NullPointerException();
                        }
                        switch (printCommand) {
                            case "WITHOUT_FILTER" -> parser.printAllTasksForUsers(idUser);

                            case "WITH_FILTER" -> {
                                System.out.println("Choose status you want to filter: Новое, В работе, Готово");
                                sc.nextLine();
                                String filterStatus = sc.nextLine();
                                if (filterStatus.equals(DEFAULT_STATUS) || filterStatus.equals(WORK_STATUS) || filterStatus.equals(COMPLETE_STATUS)) {
                                    parser.filterAllTasksForUsersByStatus(idUser, filterStatus);
                                } else {
                                    throw new InputMismatchException();
                                }
                            }
                            default -> throw new InputMismatchException();
                        }
                    }


                    case "CHANGE_TASKS_STATUS" -> {
                        System.out.println("Input task id you want to change status: ");
                        int idTask = sc.nextInt();
                        System.out.println("What status do you want to set: Новое, В работе, Готово");
                        sc.nextLine();
                        String newStatus = sc.nextLine();
                        if (newStatus.equals(DEFAULT_STATUS) || newStatus.equals(WORK_STATUS) || newStatus.equals(COMPLETE_STATUS)) {
                            parser.changeTaskStatus(idTask, newStatus);
                        } else {
                            throw new InputMismatchException();
                        }
                    }
                    default -> throw new InputMismatchException();
                }
            } catch (NullPointerException exception) {
                System.err.println("User with this id doesn't exists");
            } catch (InputMismatchException exception) {
                System.err.println("Incorrect input values");
            }
        }
    }
}
