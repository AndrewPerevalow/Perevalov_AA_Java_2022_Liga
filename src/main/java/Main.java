import com.internship.TaskTracker;
import com.internship.TaskTrackerImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        TaskTracker taskTracker = new TaskTrackerImpl();
        taskTracker.getAllTasksForUsers();
        Scanner sc = new Scanner(System.in);
        String command;
        while (true) {
            try {
                sc.nextLine();
                System.out.println("Input your command...");
                System.out.println("All commands: STOP_APP, PRINT_ALL_USER_TASKS, CHANGE_TASKS_STATUS, " +
                            "ADD_NEW_TASK, REMOVE_TASK, EDIT_TASK, ADD_NEW_USER, REMOVE_USER, CLEAN_ALL");
                command = sc.nextLine();
                switch (command) {
                    case "STOP_APP" -> System.exit(0);


                    case "PRINT_ALL_USER_TASKS" -> {
                        System.out.println("Choose the way you want to print tasks: WITHOUT_FILTER or WITH_FILTER ?");
                        String printCommand = sc.nextLine();
                        System.out.println("Input user id you want to print: ");
                        int idUser = sc.nextInt();

                        switch (printCommand) {
                            case "WITHOUT_FILTER" -> taskTracker.printAllTasksForUsers(idUser);

                            case "WITH_FILTER" -> {
                                System.out.println("Choose status you want to filter: Новое, В работе, Готово");
                                sc.nextLine();
                                String filterStatus = sc.nextLine();
                                taskTracker.filterAllTasksForUsersByStatus(idUser, filterStatus);
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
                        taskTracker.changeTaskStatus(idTask, newStatus);

                    }


                    case "ADD_NEW_TASK" -> {
                        System.out.println("Input header: ");
                        String header = sc.nextLine();
                        System.out.println("Input description: ");
                        String description = sc.nextLine();
                        System.out.println("Input user id: ");
                        int idUser = sc.nextInt();
                        System.out.println("Input date deadline(date format -> dd.MM.yyyy): ");
                        sc.nextLine();
                        String deadline = sc.nextLine();
                        taskTracker.addNewTask(header, description, idUser, deadline);
                    }

                    case "REMOVE_TASK" -> {
                        System.out.println("Input task id: ");
                        int idTask = sc.nextInt();
                        taskTracker.removeTask(idTask);
                    }

                    case "EDIT_TASK" -> {
                        System.out.println("Input task id: ");
                        int idTask = sc.nextInt();
                        System.out.println("Input another header: ");
                        sc.nextLine();
                        String header = sc.nextLine();
                        System.out.println("Input another description: ");
                        String description = sc.nextLine();
                        System.out.println("Input another user id: ");
                        int idUser = sc.nextInt();
                        System.out.println("Input another date deadline(date format -> dd.MM.yyyy): ");
                        sc.nextLine();
                        String deadline = sc.nextLine();
                        taskTracker.editTask(idTask, header, description, idUser, deadline);
                    }

                    case "ADD_NEW_USER" -> {
                        System.out.println("Input user name: ");
                        String userName = sc.nextLine();
                        taskTracker.addNewUser(userName);
                    }

                    case "REMOVE_USER" -> {
                        System.out.println("Input user id: ");
                        int idUser = sc.nextInt();
                        taskTracker.removeUser(idUser);
                    }

                    case "CLEAN_ALL" -> taskTracker.cleanAllTaskTracker();

                    default -> throw new InputMismatchException();
                }
            } catch (InputMismatchException exception) {
                System.err.println("Incorrect input values");
            }
        }
    }
}
