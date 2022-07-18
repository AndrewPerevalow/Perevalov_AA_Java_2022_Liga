package service;
/*

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.User;
import ru.internship.mvc.repo.CSVReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

class TaskTrackerInfoImplTest {

    TaskTrackerInfoImpl taskTrackerInfo;

    Initialize init;

    Map<Integer, User> usersMap;
    List<Task> tasksList;

    @BeforeEach
    void setUp() throws ParseException {
        init = new Initialize(new CSVReader());
        taskTrackerInfo = new TaskTrackerInfoImpl(init);

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

        taskTrackerInfo.setTasksList(tasksList);
        taskTrackerInfo.setUsersMap(usersMap);
        taskTrackerInfo.setDefaultStatus("Новое");
        taskTrackerInfo.setWorkStatus("В работе");
        taskTrackerInfo.setCompleteStatus("Готово");
    }

     @Nested
    @DisplayName("Print all tasks without filter")
    class PrintAllTaskWithoutFilter {

        @Nested
        @DisplayName("Negative")
        class Negative {

            @Test
            @DisplayName("Test user id doesn't exist")
            void printAllTasksForUsers_UserNotExist_ThrowInputMismatchException() {
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskTrackerInfo.printAllTasksForUsers(5L));
                assertEquals("User with this id doesn't exist", exception.getMessage());
            }

            @Test
            @DisplayName("Test user who doesn't have tasks")
            void printAllTasksForUsers_UserNotHaveAnyTask_ThrowInputMismatchException() {
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskTrackerInfo.printAllTasksForUsers(4L));
                assertEquals("This user doesn't have any tasks", exception.getMessage());
            }
        }

        @Nested
        @DisplayName("Positive")
        class Positive {

            @Test
            @DisplayName("Test user with correct input")
            void printAllTasksForUsers_CorrectInput_OutputTasks() {
                assertEquals("Задание: id = 1; Заголовок = 'task1'; Описание = 'task desc1'; Срок выполения = Sat Sep 03 00:00:00 MSK 2022; Статус = 'Новое'.\n" +
                             "Задание: id = 2; Заголовок = 'task2'; Описание = 'task desc2'; Срок выполения = Sat Aug 20 00:00:00 MSK 2022; Статус = 'В работе'.\n",
                        taskTrackerInfo.printAllTasksForUsers(1L)
                );
            }
        }
    }

    @Nested
    @DisplayName("Print all tasks with filter")
    class PrintAllTaskWithFilter {

        @Nested
        @DisplayName("Negative")
        class Negative {

            @Test
            @DisplayName("Test user id doesn't exist")
            void filterAllTasksForUsersByStatus_UserNotExist_ThrowInputMismatchException() {
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskTrackerInfo.filterAllTasksForUsersByStatus(5L, "Новое"));
                assertEquals("User with this id doesn't exist", exception.getMessage());
            }

            @Test
            @DisplayName("Test task status doesn't exist")
            void filterAllTasksForUsersByStatus_IncorrectStatus_ThrowInputMismatchException() {
                Throwable exception = assertThrows(InputMismatchException.class, () -> taskTrackerInfo.filterAllTasksForUsersByStatus(2L, anyString()));
                assertEquals("Incorrect status", exception.getMessage());
            }

            @Test
            @DisplayName("Test user doesn't have tasks with this status")
            void filterAllTasksForUsersByStatus_UserNotHaveTaskWithStatus_ThrowInputMismatchException() {
                Throwable exception1 = assertThrows(InputMismatchException.class, () -> taskTrackerInfo.filterAllTasksForUsersByStatus(3L, "В работе"));
                assertEquals("This user doesn't have tasks with this status", exception1.getMessage());
                Throwable exception2 = assertThrows(InputMismatchException.class, () -> taskTrackerInfo.filterAllTasksForUsersByStatus(2L, "Готово"));
                assertEquals("This user doesn't have tasks with this status", exception2.getMessage());
            }
        }

        @Nested
        @DisplayName("Positive")
        class Positive {

            @Test
            @DisplayName("Test user with correct new status")
            void filterAllTasksForUsersByStatus_CorrectInputStatusNew_OutputTasks() {
                assertEquals("Задание: id = 1; Заголовок = 'task1'; Описание = 'task desc1'; Срок выполения = Sat Sep 03 00:00:00 MSK 2022; Статус = 'Новое'.\n",
                        taskTrackerInfo.filterAllTasksForUsersByStatus(1L, "Новое")
                );
            }

            @Test
            @DisplayName("Test user with correct work status")
            void filterAllTasksForUsersByStatus_CorrectInputStatusInWork_OutputTasks() {
                assertEquals("Задание: id = 2; Заголовок = 'task2'; Описание = 'task desc2'; Срок выполения = Sat Aug 20 00:00:00 MSK 2022; Статус = 'В работе'.\n",
                        taskTrackerInfo.filterAllTasksForUsersByStatus(1L, "В работе")
                );
            }

            @Test
            @DisplayName("Test user with correct complete status")
            void filterAllTasksForUsersByStatus_CorrectInputStatusReady_OutputTasks() {
                assertEquals("Задание: id = 6; Заголовок = 'task6'; Описание = 'task desc6'; Срок выполения = Sat Aug 06 00:00:00 MSK 2022; Статус = 'Готово'.\n",
                        taskTrackerInfo.filterAllTasksForUsersByStatus(3L, "Готово")
                );
            }
        }
    }
}*/
