package ru.internship.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.internship.mvc.repo.Reader;
import ru.internship.mvc.repo.Writer;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.User;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TaskTrackerImpl implements TaskTracker {

    private static String DEFAULT_STATUS;

    @Value("${statuses.default-status}")
    public void setDefaultStatus(String status) {
        TaskTrackerImpl.DEFAULT_STATUS = status;
    }

    private static String WORK_STATUS;

    @Value("${statuses.work-status}")
    public void setWorkStatus(String status) {
        TaskTrackerImpl.WORK_STATUS = status;
    }

    private static String COMPLETE_STATUS;

    @Value("${statuses.complete-status}")
    public void setCompleteStatus(String status) {
        TaskTrackerImpl.COMPLETE_STATUS = status;
    }

    @Value("${files.user-file}")
    private String userFile;
    @Value("${files.task-file}")
    private String taskFile;

    private final Reader csvReader;
    private final Writer csvWriter;
    private Map<Integer, User> usersMap;
    private List<Task> tasksList;

    @Autowired
    TaskTrackerImpl(Reader csvReader, Writer csvWriter) {
        this.csvReader = csvReader;
        this.csvWriter = csvWriter;
    }

    @PostConstruct
    public void firstInit() {
        usersMap = csvReader.getMapUsers(userFile);
        tasksList = csvReader.getListTasks(taskFile);
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
        if (usersMap.containsKey(idUser)) {
            List<Task> listTasks = usersMap.get(idUser).getTasks();
            if (listTasks.size() != 0) {
                System.out.println(listTasks.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\, ", ""));
            } else {
                System.err.println("This user doesn't have any tasks");
            }
        } else {
            System.err.println("User with this id doesn't exists");
        }
    }

    @Override
    public void filterAllTasksForUsersByStatus(int idUser, String status) {
        if (usersMap.containsKey(idUser)) {
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
                System.err.println("This user doesn't have tasks with this status");
            }
        } else {
            System.err.println("User with this id doesn't exists");
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
        if (usersMap.containsKey(idUser)) {
            try {
                Date dateDeadline = new SimpleDateFormat("dd.MM.yyyy").parse(deadline);
                if (isValidInput(header,description,dateDeadline)) {
                    Task task = new Task(tasksList.size() + 1, header, description, idUser, dateDeadline, DEFAULT_STATUS);
                    tasksList.add(task);
                    usersMap.get(task.getIdUser()).getTasks().add(task);
                    csvWriter.writeListTasks(taskFile, tasksList, usersMap);
                    System.out.println("New task added");
                } else {
                    throw new InputMismatchException();
                }
            } catch (ParseException exception) {
                System.err.println("Parse fail" + exception.getMessage());
            } catch (InputMismatchException exception) {
                System.err.println("Incorrect input values");
            }
        } else {
            System.err.println("User with this id doesn't exists");
        }
    }

    @Override
    public void removeTask(int idTask) {
        if (tasksList.stream().anyMatch(task -> task.getId() == idTask)) {
            tasksList.removeIf(task -> task.getId() == idTask);
            for (User user : usersMap.values()) {
                user.getTasks().removeIf(task -> task.getId() == idTask);
            }
            csvWriter.writeListTasks(taskFile, tasksList, usersMap);
            System.out.println("Task deleted");
        } else {
            System.err.println("Task with this id doesn't exists");
        }
    }

    @Override
    public void addNewUser(String userName) {
        if (userName.trim().length() != 0) {
            usersMap.put(usersMap.size() + 1, new User(usersMap.size() + 1, userName, new ArrayList<>()));
            csvWriter.writeUsers(userFile, usersMap);
            System.out.println("New user added");
        } else {
            System.err.println("Incorrect input values");
        }
    }

    @Override
    public void removeUser(int idUser) {
        if (usersMap.containsKey(idUser)) {
            usersMap.remove(idUser);
            csvWriter.writeUsers(userFile, usersMap);
            System.out.println("User deleted");
        } else {
            System.err.println("User with this id doesn't exists");
        }
    }

    @Override
    public void editTask(int idTask, String header, String description, int idUser, String deadline) {
        if (usersMap.containsKey(idUser)) {
            try {
                Date dateDeadline = new SimpleDateFormat("dd.MM.yyyy").parse(deadline);
                if (isValidInput(header,description,dateDeadline)) {
                    Task task = tasksList.stream().filter(t -> t.getId() == idTask).findFirst().orElseThrow(NoSuchElementException::new);
                    task.setHeader(header);
                    task.setDescription(description);
                    task.setIdUser(idUser);
                    task.setDeadline(dateDeadline);
                    if (!(usersMap.get(task.getIdUser()).getTasks().contains(task))) {
                        usersMap.get(task.getIdUser()).getTasks().add(task);
                    }
                    csvWriter.writeListTasks(taskFile, tasksList, usersMap);
                    System.out.println("Task edited");
                } else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException exception) {
                System.err.println("Incorrect input values");
            } catch (NoSuchElementException exception) {
                System.err.println("Task with this id doesn't exists");
            } catch (ParseException exception) {
                System.err.println("Parse fail" + exception.getMessage());
            }
        } else {
            System.err.println("User with this id doesn't exists");
        }
    }

    @Override
    public void cleanAllTaskTracker() {
        tasksList.clear();
        usersMap.clear();
        csvWriter.writeListTasks(taskFile, tasksList, usersMap);
        csvWriter.writeUsers(userFile, usersMap);
        System.out.println("All users and task deleted");
    }

    private boolean isValidInput(String header, String description, Date deadline) {
        if (header.trim().length() == 0) return false;
        if (description.trim().length() == 0) return false;
        return isDateValid(deadline);
    }

    private boolean isDateValid(Date inputDate) {
        if (inputDate == null) {
            return false;
        }
        Date currentDate = new Date();
        int result = inputDate.compareTo(currentDate);
        return result >= 0;
    }
}
