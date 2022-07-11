package ru.internship.mvc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.User;
import ru.internship.mvc.repo.CSVReader;
import ru.internship.mvc.repo.CSVWriter;
import ru.internship.mvc.repo.Writer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserTrackerImplTest {

    UserTrackerImpl userTracker;

    Initialize init;
    Writer csvWriter;

    Map<Integer, User> usersMap;
    List<Task> tasksList;

    @BeforeEach
    void setUp() throws ParseException {
        init = new Initialize(new CSVReader());
        csvWriter = new CSVWriter();
        userTracker = new UserTrackerImpl(init, csvWriter);

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
        userTracker.setUsersMap(usersMap);
    }

    @Nested
    @DisplayName("Add new user")
    class AddNewUser {

        @Nested
        @DisplayName("Negative")
        class Negative {

            @Test
            @DisplayName("Test incorrect user name")
            void addNewUser_IncorrectUserName_ThrowInputMismatchException() {
                Throwable exception = assertThrows(InputMismatchException.class, () -> userTracker.addNewUser(".d_231"));
                assertEquals("Incorrect user name", exception.getMessage());
            }
        }

        @Nested
        @DisplayName("Positive")
        class Positive {

            @Test
            @DisplayName("Test correct user name")
            void addNewUser_CorrectUserName_AddedNewUser() {
                Map<Integer, User> userMap = new HashMap<>(usersMap);
                userTracker.setUsersMap(userMap);
                userTracker.addNewUser("Дарья");
                assertEquals(5, userTracker.getUsersMap().size());
                assertEquals("Дарья", userTracker.getUsersMap().get(5).getUserName());
            }

        }
    }

    @Nested
    @DisplayName("Remove user")
    class RemoveUser {

        @Nested
        @DisplayName("Negative")
        class Negative {

            @Test
            @DisplayName("Test user id doesn't exist")
            void removeUser_UserNotExist_ThrowInputMismatchException() {
                Throwable exception = assertThrows(InputMismatchException.class, () -> userTracker.removeUser(12));
                assertEquals("User with this id doesn't exist", exception.getMessage());
            }
        }

        @Nested
        @DisplayName("Positive")
        class Positive {

            @Test
            @DisplayName("Test correct user id")
            void removeUser_CorrectUserId_RemovedNewUser() {
                Map<Integer, User> userMap = new HashMap<>(usersMap);
                userTracker.setUsersMap(userMap);
                userTracker.removeUser(2);
                assertEquals(3, userTracker.getUsersMap().size());
                assertTrue(userTracker.getUsersMap().values().stream().noneMatch(user -> user.getUserName().equals("Андрей")));
            }
        }
    }
}