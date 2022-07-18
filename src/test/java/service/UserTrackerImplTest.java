package service;
/*

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.User;

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
                assertEquals("Дарья", userTracker.getUsersMap().get(5).getName());
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
                Throwable exception = assertThrows(InputMismatchException.class, () -> userTracker.removeUser(12L));
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
                userTracker.removeUser(2L);
                assertEquals(3, userTracker.getUsersMap().size());
                assertTrue(userTracker.getUsersMap().values().stream().noneMatch(user -> user.getName().equals("Андрей")));
            }
        }
    }
}*/
