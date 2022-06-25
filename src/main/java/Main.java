import com.internship.TaskTracker;
import com.internship.TaskTrackerImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private final static String DEFAULT_STATUS = "Новое";
    private final static String WORK_STATUS = "В работе";
    private final static String COMPLETE_STATUS = "Готово";


    public static void main(String[] args) {
        TaskTracker taskTracker = new TaskTrackerImpl();
        taskTracker.getAllTasksForUsers();
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
                        if (idUser < 1 || idUser > taskTracker.getCountUsers()) {
                            throw new NullPointerException();
                        }
                        switch (printCommand) {
                            case "WITHOUT_FILTER" -> taskTracker.printAllTasksForUsers(idUser);

                            case "WITH_FILTER" -> {
                                System.out.println("Choose status you want to filter: Новое, В работе, Готово");
                                sc.nextLine();
                                String filterStatus = sc.nextLine();
                                if (filterStatus.equals(DEFAULT_STATUS) || filterStatus.equals(WORK_STATUS) || filterStatus.equals(COMPLETE_STATUS)) {
                                    taskTracker.filterAllTasksForUsersByStatus(idUser, filterStatus);
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
                            taskTracker.changeTaskStatus(idTask, newStatus);
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
