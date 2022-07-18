package service;
/*

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.User;
import ru.internship.mvc.repo.TaskRepo;
import ru.internship.mvc.repo.UserRepo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

class TaskServiceTest {

    TaskService taskService;

    @Mock
    TaskRepo taskRepo;
    @Mock
    UserRepo userRepo;

    Map<Integer, User> usersMap;
    List<Task> tasksList;

    @BeforeEach
    void setUp() throws ParseException {
        MockitoAnnotations.openMocks(this);

        taskService = new TaskService(taskRepo, userRepo);

        tasksList = List.of(new Task(1L, "task1", "task desc1", 1L, new SimpleDateFormat("dd.MM.yyyy").parse("03.09.2022"), "Новое"),
                new Task(2L, "task2", "task desc2", 1L, new SimpleDateFormat("dd.MM.yyyy").parse("20.08.2022"), "В работе"),
                new Task(3L, "task3", "task desc3", 2L, new SimpleDateFormat("dd.MM.yyyy").parse("21.09.2022"), "Новое"),
                new Task(4L, "task4", "task desc4", 3L, new SimpleDateFormat("dd.MM.yyyy").parse("28.08.2022"), "Новое"),
                new Task(5L, "task5", "task desc5", 3L, new SimpleDateFormat("dd.MM.yyyy").parse("16.08.2022"), "Новое"),
                new Task(6L, "task6", "task desc6", 3L, new SimpleDateFormat("dd.MM.yyyy").parse("06.08.2022"), "Готово")
        );

        usersMap = Map.of(1, new User(1L, "Сергей", new ArrayList<>()),
                2, new User(2L, "Андрей", new ArrayList<>()),
                3, new User(3L, "Петр", new ArrayList<>()),
                4, new User(4L, "Виктор", new ArrayList<>())
        );

        for (Task task : tasksList) {
            if (usersMap.containsKey(task.getIdUser())) {
                usersMap.get(task.getIdUser()).getTasks().add(task);
            }
        }

        */
/*taskTracker.setTasksList(tasksList);
        taskTracker.setUsersMap(usersMap);
        taskTracker.setDefaultStatus("Новое");
        taskTracker.setWorkStatus("В работе");
        taskTracker.setCompleteStatus("Готово");*//*

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
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskService.changeTaskStatus(2L, anyString()));
                assertEquals("Incorrect status", exception.getMessage());
            }

            @Test
            @DisplayName("Test task id doesn't exist")
            void changeTaskStatus_TaskNotExist_ThrowInputMismatchException() {
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskService.changeTaskStatus(8L, "Готово"));
                assertEquals("Task with this id doesn't exist", exception.getMessage());
            }
        }

        @Nested
        @DisplayName("Positive")
        class Positive {

            @Test
            @DisplayName("Test task with correct id and status")
            void changeTaskStatus_CorrectInput_ChangeStatus() {
                taskService.changeTaskStatus(5L, "В работе");
                assertEquals("В работе", tasksList.get(4).getStatus());
                taskService.changeTaskStatus(4L, "Готово");
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
            void addNewTask_UserNotExist_ThrowInputMismatchException() throws ParseException {
                Date validDate = new SimpleDateFormat("dd.MM.yyyy").parse("20.09.2022");
//                Throwable exception = assertThrows(InputMismatchException.class, () -> taskService.addNewTask("Header", "Task desc", 8L, validDate));
//                assertEquals("User with this id doesn't exist", exception.getMessage());
            }

            @Test
            @DisplayName("Test invalid input header")
            void addNewTask_InvalidInputHeader_ThrowInputMismatchException() throws ParseException {
                Date validDate = new SimpleDateFormat("dd.MM.yyyy").parse("20.09.2022");
//                Throwable exception = assertThrows(InputMismatchException.class, () -> taskService.addNewTask("", "Task desc", 2L, validDate));
//                assertEquals("Incorrect input values", exception.getMessage());
            }

            @Test
            @DisplayName("Test invalid input description")
            void addNewTask_InvalidInputDescription_ThrowInputMismatchException() throws ParseException {
                Date validDate = new SimpleDateFormat("dd.MM.yyyy").parse("20.09.2022");
//                Throwable exception = assertThrows(InputMismatchException.class, () -> taskService.addNewTask("Header", "", 2L, validDate));
//                assertEquals("Incorrect input values", exception.getMessage());
            }

            @Test
            @DisplayName("Test invalid input date")
            void addNewTask_InvalidInputDate_ThrowInputMismatchException() throws ParseException {
                Date invalidDate = new SimpleDateFormat("dd.MM.yyyy").parse("20.09.1998");
//                Throwable exception = assertThrows(InputMismatchException.class, () -> taskService.addNewTask("Header", "Task desc", 2L, invalidDate));
//                assertEquals("Incorrect input values", exception.getMessage());
            }

            @Test
            @DisplayName("Test invalid input date parse")
            void addNewTask_InvalidInputDateParse_ErrorOutput() {
                String invalidDate = "\"20.09\"";
//                Throwable exception = assertThrows(ParseException.class, () -> taskService.addNewTask("Header", "Task desc", 2L, new SimpleDateFormat("dd.MM.yyyy").parse("20.09")));
//                assertEquals("Unparseable date: " + invalidDate, exception.getMessage());
            }
        }

        @Nested
        @DisplayName("Positive")
        class Positive {

            @Test
            @DisplayName("Test valid input values")
            void addNewTask_ValidInput_AddedNewTask() throws ParseException {
                Task task = new Task();
                task.setHeader("Header");
                task.setDescription("Task desc");
                task.setIdUser(2L);
                task.setDeadline(new SimpleDateFormat("yyyy-MM-dd").parse("2022-09-20"));
                task.setStatus("Новое");
                List<Task> taskList = new ArrayList<>();
                taskList.add(task);
                User user = new User();
                user.setName("Name");
                user.setTasks(taskList);
                Mockito.when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
                Mockito.when(taskRepo.save(any(Task.class))).thenReturn(task);
                Task createdTask = taskService.addNewTask(task);

                assertEquals(createdTask.getHeader(), task.getHeader());
                assertEquals(createdTask.getDescription(), task.getDescription());
                assertEquals(createdTask.getIdUser(), task.getIdUser());
                assertEquals(createdTask.getDeadline(), task.getDeadline());
                assertEquals(createdTask.getStatus(), task.getStatus());
                Mockito.verify(taskRepo).save(task);
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
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskService.removeTask(25L));
                assertEquals("Task with this id doesn't exist", exception.getMessage());
            }
        }

        @Nested
        @DisplayName("Positive")
        class Positive {

            @Test
            @DisplayName("Test valid input values")
            void removeTask_ValidId_RemovedTask() {
                */
/*List<Task> taskList = new ArrayList<>(tasksList);
                taskTracker.setTasksList(taskList);
                taskTracker.removeTask(1L);
                assertEquals(5, taskTracker.getTasksList().size());
                assertEquals(0, taskTracker.getTasksList().stream().filter(task -> task.getId() == 1).count());

                assertEquals(1, usersMap.get(1).getTasks().size());
                assertEquals(0, usersMap.get(1).getTasks().stream().filter(task -> task.getId() == 1).count());*//*

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
            void editTask_UserNotExist_ThrowInputMismatchException() throws ParseException {
                Date validDate = new SimpleDateFormat("dd.MM.yyyy").parse("27.10.2022");
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskService.editTask(2L, "Header", "Task desc", 9L, validDate));
                assertEquals("User with this id doesn't exist", exception.getMessage());
            }

            @Test
            @DisplayName("Test invalid input header")
            void editTask_InvalidInputHeader_ThrowInputMismatchException() throws ParseException {
                Date validDate = new SimpleDateFormat("dd.MM.yyyy").parse("27.10.2022");
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskService.editTask(2L, "", "Task desc", 2L, validDate));
                assertEquals("Incorrect input values", exception.getMessage());
            }

            @Test
            @DisplayName("Test invalid input description")
            void editTask_InvalidInputDescription_ThrowInputMismatchException() throws ParseException {
                Date validDate = new SimpleDateFormat("dd.MM.yyyy").parse("27.10.2022");
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskService.editTask(2L, "Header", "", 2L, validDate));
                assertEquals("Incorrect input values", exception.getMessage());
            }

            @Test
            @DisplayName("Test invalid input date")
            void editTask_InvalidInputDate_ThrowInputMismatchException() throws ParseException {
                Date invalidDate = new SimpleDateFormat("dd.MM.yyyy").parse("20.09.1998");
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskService.editTask(2L, "Header", "Task desc", 2L, invalidDate));
                assertEquals("Incorrect input values", exception.getMessage());
            }

            @Test
            @DisplayName("Test invalid input date parse")
            void editTask_InvalidInputDateParse_ErrorOutput() {
                String invalidDate = "\"adasf\"";
                Throwable exception = assertThrows(ParseException.class, () -> taskService.editTask(2L, "Header", "Task desc", 2L, new SimpleDateFormat("dd.MM.yyyy").parse("adasf")));
                assertEquals("Unparseable date: " + invalidDate, exception.getMessage());
            }

            @Test
            @DisplayName("Test task id doesn't exist")
            void editTask_InvalidId_ErrorOutput() throws ParseException {
                Date validDate = new SimpleDateFormat("dd.MM.yyyy").parse("27.10.2022");
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskService.editTask(12L, "Header", "Task desc", 2L, validDate));
                assertEquals("Task with this id doesn't exist", exception.getMessage());
            }
        }

        @Nested
        @DisplayName("Positive")
        class Positive {

            @Test
            @DisplayName("Test valid input values")
            void editTask_ValidInput_EditedTask() throws ParseException {
                */
/*List<Task> taskList = new ArrayList<>(tasksList);
                taskTracker.setTasksList(taskList);
                taskTracker.editTask(4L, "New Header", "New task desc", 2L, new SimpleDateFormat("dd.MM.yyyy").parse("29.10.2022"));
                assertEquals(6, taskTracker.getTasksList().size());
                assertEquals("New Header", taskTracker.getTasksList().get(3).getHeader());
                assertEquals("New task desc", taskTracker.getTasksList().get(3).getDescription());
                assertEquals(2, taskTracker.getTasksList().get(3).getIdUser());
                assertEquals(new SimpleDateFormat("dd.MM.yyyy").parse("29.10.2022"), taskTracker.getTasksList().get(3).getDeadline());


                assertEquals(2, usersMap.get(2).getTasks().size());
                assertEquals(1, usersMap.get(2).getTasks().stream().filter(task -> task.getHeader().equals("New Header")).count());
                assertEquals(1, usersMap.get(2).getTasks().stream().filter(task -> task.getDescription().equals("New task desc")).count());
                assertEquals(2, usersMap.get(2).getTasks().stream().filter(task -> task.getIdUser() == 2).count());
                assertEquals(new SimpleDateFormat("dd.MM.yyyy").parse("29.10.2022"), usersMap.get(2).getTasks().get(1).getDeadline());*//*

            }
        }
    }

    @Nested
    @DisplayName("Clean all task tracker")
    class CleanAllTaskTracker {

        @Test
        @DisplayName("Test clean all tasks and users")
        void cleanAllTaskTracker_RemovedAllUsersAndTasks() {
            */
/*Map<Integer, User> userMap = new HashMap<>(usersMap);
            taskTracker.setUsersMap(userMap);
            List<Task> taskList = new ArrayList<>(tasksList);
            taskTracker.setTasksList(taskList);
            taskTracker.cleanAllTaskTracker();
            assertEquals(0, taskTracker.getTasksList().size());
            assertEquals(0, taskTracker.getUsersMap().size());*//*

        }
    }
}
*/
