package ru.internship.mvc.service;

import org.junit.jupiter.api.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.User;
import ru.internship.mvc.repo.CSVReader;
import ru.internship.mvc.repo.CSVWriter;
import ru.internship.mvc.repo.Writer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(OutputCaptureExtension.class)
class TaskTrackerImplTest {

    TaskTrackerImpl taskTracker;

    Initialize init;
    Writer csvWriter;

    Map<Integer, User> usersMap;
    List<Task> tasksList;

    @BeforeEach
    void setUp() throws ParseException {
        init = new Initialize(new CSVReader());
        csvWriter = new CSVWriter();
        taskTracker = new TaskTrackerImpl(init, csvWriter);

        tasksList = List.of(new Task(1, "task1", "task desc1", 1, new SimpleDateFormat("dd.MM.yyyy").parse("03.09.2022"), "Новое"),
                new Task(2, "task2", "task desc2", 1, new SimpleDateFormat("dd.MM.yyyy").parse("20.08.2022"), "В работе"),
                new Task(3, "task3", "task desc3", 2, new SimpleDateFormat("dd.MM.yyyy").parse("21.09.2022"), "Новое"),
                new Task(4, "task4", "task desc4", 3, new SimpleDateFormat("dd.MM.yyyy").parse("28.08.2022"), "Новое"),
                new Task(5, "task5", "task desc5", 3, new SimpleDateFormat("dd.MM.yyyy").parse("16.08.2022"), "Новое"),
                new Task(6, "task6", "task desc6", 3, new SimpleDateFormat("dd.MM.yyyy").parse("06.08.2022"), "Готово")
        );

        usersMap = Map.of(1, new User(1, "Сергей", new ArrayList<>()),
                2, new User(2, "Андрей", new ArrayList<>()),
                3, new User(3, "Петр", new ArrayList<>()),
                4, new User(4, "Виктор", new ArrayList<>())
        );

        for (Task task : tasksList) {
            if (usersMap.containsKey(task.getIdUser())) {
                usersMap.get(task.getIdUser()).getTasks().add(task);
            }
        }

        taskTracker.setTasksList(tasksList);
        taskTracker.setUsersMap(usersMap);
        taskTracker.setDefaultStatus("Новое");
        taskTracker.setWorkStatus("В работе");
        taskTracker.setCompleteStatus("Готово");
    }

    @Nested
    @DisplayName("Change task status")
    class ChangeTaskStatus {

        @Nested
        @DisplayName("Negative")
        class Negative {

            @Test
            @DisplayName("Test task status doesn't exist")
            void changeTaskStatus_IncorrectStatus_ThrowInputMismatchException() {
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskTracker.changeTaskStatus(2, anyString()));
                assertEquals("Incorrect status", exception.getMessage());
            }

            @Test
            @DisplayName("Test task id doesn't exist")
            void changeTaskStatus_TaskNotExist_ThrowInputMismatchException() {
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskTracker.changeTaskStatus(8, "Готово"));
                assertEquals("Task with this id doesn't exist", exception.getMessage());
            }
        }

        @Nested
        @DisplayName("Positive")
        class Positive {

            @Test
            @DisplayName("Test task with correct id and status")
            void changeTaskStatus_CorrectInput_ChangeStatus() {
                taskTracker.changeTaskStatus(5, "В работе");
                assertEquals("В работе", tasksList.get(4).getStatus());
                taskTracker.changeTaskStatus(4, "Готово");
                assertEquals("Готово", tasksList.get(3).getStatus());
            }
        }
    }

    @Nested
    @DisplayName("Add new task")
    class AddNewTask {

        @Nested
        @DisplayName("Negative")
        class Negative {

            @Test
            @DisplayName("Test user id doesn't exist")
            void addNewTask_UserNotExist_ThrowInputMismatchException() {
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskTracker.addNewTask("Header", "Task desc", 8, "20.09.2022"));
                assertEquals("User with this id doesn't exist", exception.getMessage());
            }

            @Test
            @DisplayName("Test invalid input header")
            void addNewTask_InvalidInputHeader_ThrowInputMismatchException() {
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskTracker.addNewTask("", "Task desc", 2, "20.09.2022"));
                assertEquals("Incorrect input values", exception.getMessage());
            }

            @Test
            @DisplayName("Test invalid input description")
            void addNewTask_InvalidInputDescription_ThrowInputMismatchException() {
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskTracker.addNewTask("Header", "", 2, "20.09.2022"));
                assertEquals("Incorrect input values", exception.getMessage());
            }

            @Test
            @DisplayName("Test invalid input date")
            void addNewTask_InvalidInputDate_ThrowInputMismatchException() {
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskTracker.addNewTask("Header", "Task desc", 2, "20.09.1998"));
                assertEquals("Incorrect input values", exception.getMessage());
            }

            @Test
            @DisplayName("Test invalid input date parse")
            void addNewTask_InvalidInputDateParse_ErrorOutput(CapturedOutput output) {
                String invalidDate = "20.09";
                taskTracker.addNewTask("Header", "Task desc", 2, invalidDate);
                assertThat(output).isEqualToNormalizingPunctuationAndWhitespace("Parse fail: Unparseable date: " + invalidDate);
            }
        }

        @Nested
        @DisplayName("Positive")
        class Positive {

            @Test
            @DisplayName("Test valid input values")
            void addNewTask_ValidInput_AddedNewTask() throws ParseException {
                List<Task> taskList = new ArrayList<>(tasksList);
                taskTracker.setTasksList(taskList);
                taskTracker.addNewTask("Header", "Task desc", 2, "20.09.2022");
                assertEquals(7, taskTracker.getTasksList().size());
                assertEquals("Header", taskTracker.getTasksList().get(6).getHeader());
                assertEquals("Task desc", taskTracker.getTasksList().get(6).getDescription());
                assertEquals(2, taskTracker.getTasksList().get(6).getIdUser());
                assertEquals(new SimpleDateFormat("dd.MM.yyyy").parse("20.09.2022"), taskTracker.getTasksList().get(6).getDeadline());
                assertEquals("Новое", taskTracker.getTasksList().get(6).getStatus());

                assertEquals(2, usersMap.get(2).getTasks().size());
                assertEquals(1, usersMap.get(2).getTasks().stream().filter(task -> task.getHeader().equals("Header")).count());
                assertEquals(1, usersMap.get(2).getTasks().stream().filter(task -> task.getDescription().equals("Task desc")).count());
                assertEquals(2, usersMap.get(2).getTasks().stream().filter(task -> task.getIdUser() == 2).count());
                assertEquals(new SimpleDateFormat("dd.MM.yyyy").parse("20.09.2022"), usersMap.get(2).getTasks().get(1).getDeadline());
                assertEquals(2, usersMap.get(2).getTasks().stream().filter(task -> task.getStatus().equals("Новое")).count());
            }
        }
    }

    @Nested
    @DisplayName("Remove task")
    class RemoveTask {

        @Nested
        @DisplayName("Negative")
        class Negative {

            @Test
            @DisplayName("Test task id doesn't exist")
            void removeTask_InvalidId_ThrowInputMismatchException() {
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskTracker.removeTask(25));
                assertEquals("Task with this id doesn't exist", exception.getMessage());
            }
        }

        @Nested
        @DisplayName("Positive")
        class Positive {

            @Test
            @DisplayName("Test valid input values")
            void removeTask_ValidId_RemovedTask() {
                List<Task> taskList = new ArrayList<>(tasksList);
                taskTracker.setTasksList(taskList);
                taskTracker.removeTask(1);
                assertEquals(5, taskTracker.getTasksList().size());
                assertEquals(0, taskTracker.getTasksList().stream().filter(task -> task.getId() == 1).count());

                assertEquals(1, usersMap.get(1).getTasks().size());
                assertEquals(0, usersMap.get(1).getTasks().stream().filter(task -> task.getId() == 1).count());
            }
        }
    }

    @Nested
    @DisplayName("Edit task")
    class EditTask {

        @Nested
        @DisplayName("Negative")
        class Negative {

            @Test
            @DisplayName("Test user id doesn't exist")
            void editTask_UserNotExist_ThrowInputMismatchException() {
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskTracker.editTask(2, "Header", "Task desc", 9, "27.10.2022"));
                assertEquals("User with this id doesn't exist", exception.getMessage());
            }

            @Test
            @DisplayName("Test invalid input header")
            void editTask_InvalidInputHeader_ThrowInputMismatchException() {
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskTracker.editTask(2, "", "Task desc", 2, "27.10.2022"));
                assertEquals("Incorrect input values", exception.getMessage());
            }

            @Test
            @DisplayName("Test invalid input description")
            void editTask_InvalidInputDescription_ThrowInputMismatchException() {
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskTracker.editTask(2, "Header", "", 2, "27.10.2022"));
                assertEquals("Incorrect input values", exception.getMessage());
            }

            @Test
            @DisplayName("Test invalid input date")
            void editTask_InvalidInputDate_ThrowInputMismatchException() {
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskTracker.editTask(2, "Header", "Task desc", 2, "20.09.1998"));
                assertEquals("Incorrect input values", exception.getMessage());
            }

            @Test
            @DisplayName("Test invalid input date parse")
            void editTask_InvalidInputDateParse_ErrorOutput(CapturedOutput output) {
                String invalidDate = "adasf";
                taskTracker.editTask(2, "Header", "Task desc", 2, invalidDate);
                assertThat(output).isEqualToNormalizingPunctuationAndWhitespace("Parse fail: Unparseable date: " + invalidDate);
            }

            @Test
            @DisplayName("Test task id doesn't exist")
            void editTask_InvalidId_ErrorOutput() {
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskTracker.editTask(12, "Header", "Task desc", 2, "27.10.2022"));
                assertEquals("Task with this id doesn't exist", exception.getMessage());
            }
        }

        @Nested
        @DisplayName("Positive")
        class Positive {

            @Test
            @DisplayName("Test valid input values")
            void editTask_ValidInput_EditedTask() throws ParseException {
                List<Task> taskList = new ArrayList<>(tasksList);
                taskTracker.setTasksList(taskList);
                taskTracker.editTask(4, "New Header", "New task desc", 2, "29.10.2022");
                assertEquals(6, taskTracker.getTasksList().size());
                assertEquals("New Header", taskTracker.getTasksList().get(3).getHeader());
                assertEquals("New task desc", taskTracker.getTasksList().get(3).getDescription());
                assertEquals(2, taskTracker.getTasksList().get(3).getIdUser());
                assertEquals(new SimpleDateFormat("dd.MM.yyyy").parse("29.10.2022"), taskTracker.getTasksList().get(3).getDeadline());


                assertEquals(2, usersMap.get(2).getTasks().size());
                assertEquals(1, usersMap.get(2).getTasks().stream().filter(task -> task.getHeader().equals("New Header")).count());
                assertEquals(1, usersMap.get(2).getTasks().stream().filter(task -> task.getDescription().equals("New task desc")).count());
                assertEquals(2, usersMap.get(2).getTasks().stream().filter(task -> task.getIdUser() == 2).count());
                assertEquals(new SimpleDateFormat("dd.MM.yyyy").parse("29.10.2022"), usersMap.get(2).getTasks().get(1).getDeadline());
            }
        }
    }

    @Nested
    @DisplayName("Clean all task tracker")
    class CleanAllTaskTracker {

        @Test
        @DisplayName("Test clean all tasks and users")
        void cleanAllTaskTracker_RemovedAllUsersAndTasks() {
            Map<Integer, User> userMap = new HashMap<>(usersMap);
            taskTracker.setUsersMap(userMap);
            List<Task> taskList = new ArrayList<>(tasksList);
            taskTracker.setTasksList(taskList);
            taskTracker.cleanAllTaskTracker();
            assertEquals(0, taskTracker.getTasksList().size());
            assertEquals(0, taskTracker.getUsersMap().size());
        }
    }
}