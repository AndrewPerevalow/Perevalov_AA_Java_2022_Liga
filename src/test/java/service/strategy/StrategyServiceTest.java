package service.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.internship.mvc.dto.InputTaskDto;
import ru.internship.mvc.dto.InputUserDto;
import ru.internship.mvc.dto.UserFindMaxTasksDto;
import ru.internship.mvc.model.Project;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.User;
import ru.internship.mvc.repo.ProjectRepo;
import ru.internship.mvc.repo.TaskRepo;
import ru.internship.mvc.repo.UserRepo;
import ru.internship.mvc.service.TaskInfoService;
import ru.internship.mvc.service.TaskService;
import ru.internship.mvc.service.UserInfoService;
import ru.internship.mvc.service.UserService;
import ru.internship.mvc.service.strategy.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

class StrategyServiceTest {

    @Mock
    TaskInfoService taskInfoService;
    @Mock
    TaskService taskService;
    @Mock
    UserService userService;
    @Mock
    UserInfoService userInfoService;
    @Mock
    UserRepo userRepo;
    @Mock
    ProjectRepo projectRepo;
    @Mock
    TaskRepo taskRepo;

    List<Task> taskList;
    List<User> userList;
    UserFindMaxTasksDto userFindMaxTasksDTO;
    Project project;

    {
        try {
            userList = List.of(new User(1L, "testName1", "testSurname1", "testPatronymic1", "testLogin1", "testEmail@mail.com1", "testPassword1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),
                               new User(2L, "testName2", "testSurname2", "testPatronymic2", "testLogin2", "testEmail@mail.com2", "testPassword2", new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),
                               new User(3L, "testName3", "testSurname3", "testPatronymic3", "testLogin3", "testEmail@mail.com3", "testPassword3", new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
            );
            taskList = List.of(new Task(1L, "task1", "task desc1", userList.get(0), new SimpleDateFormat("yyyy-MM-dd").parse("2022-09-03"), "Новое", new ArrayList<>(), new Project()),
                    new Task(2L, "task2", "task desc2", userList.get(0), new SimpleDateFormat("yyyy-MM-dd").parse("2022-08-20"), "В работе", new ArrayList<>(), new Project()),
                    new Task(3L, "task3", "task desc3", userList.get(1), new SimpleDateFormat("yyyy-MM-dd").parse("2022-09-21"), "Новое", new ArrayList<>(), new Project()),
                    new Task(4L, "task4", "task desc4", userList.get(2), new SimpleDateFormat("yyyy-MM-dd").parse("2022-08-28"), "Новое", new ArrayList<>(), new Project()),
                    new Task(5L, "task5", "task desc5", userList.get(2), new SimpleDateFormat("yyyy-MM-dd").parse("2022-08-16"), "Новое", new ArrayList<>(), new Project()),
                    new Task(6L, "task6", "task desc6", userList.get(2), new SimpleDateFormat("yyyy-MM-dd").parse("2022-08-06"), "Готово", new ArrayList<>(), new Project())
            );
            userList.get(0).setTasks(taskList.subList(0,1));
            userList.get(0).setTasks(taskList.subList(2,2));
            userList.get(0).setTasks(taskList.subList(3,5));

            project = new Project(1L, "header", "desc", userList, taskList);

            userFindMaxTasksDTO = new UserFindMaxTasksDto();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        MockitoAnnotations.openMocks(this);
        Mockito.when(taskInfoService.printAllTasksForUsers(anyLong()))
                .thenReturn(taskList.stream()
                        .filter(task -> task.getUser().getId() == 3).toList()
                );
        Mockito.when(taskInfoService.filterAllTasksForUsersByStatus(anyLong(), anyString()))
                .thenReturn(taskList.stream()
                        .filter(task -> task.getUser().getId() == 3)
                        .filter(task -> task.getStatus().equals("Новое")).toList()
                );
        Mockito.when(taskService.changeTaskStatus(anyLong(), anyString())).thenReturn(taskList.get(1));
        Mockito.when(taskService.addNewTask(anyLong(), anyLong(), any(InputTaskDto.class))).thenReturn(taskList.get(0));
        Mockito.when(taskService.removeTask(anyLong())).thenReturn("Task removed");
        Mockito.when(taskService.editTask(anyLong(), anyLong(), anyLong(), any(InputTaskDto.class))).thenReturn(taskList.get(2));
        Mockito.when(userService.addNewUser(anyLong(), any(InputUserDto.class))).thenReturn(userList.get(0));
        Mockito.when(userService.removeUser(anyLong())).thenReturn("User removed");
        Mockito.when(taskService.cleanAllTaskTracker()).thenReturn("All clean");
        Mockito.when(projectRepo.findById(anyLong())).thenReturn(Optional.ofNullable(project));
        Mockito.when(taskRepo.findById(anyLong())).thenReturn(Optional.ofNullable(taskList.get(2)));
        Mockito.when(userRepo.findById(anyLong())).thenReturn(Optional.ofNullable(userList.get(2)));
        try {
            Mockito.when(userInfoService.findByMaxTasksCount(anyString(),any(),any())).thenReturn(userFindMaxTasksDTO);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    Strategy stopApplicationStrategy = new StopApplicationStrategy(Mockito.mock(TaskService.class));
    Strategy printAllTasksStrategy = new PrintAllTaskTrackerImplStrategy(taskInfoService);
    Strategy printAllFilterTasksStrategy = new FilterAllTasksForUsersByStatusStrategy(taskInfoService);
    Strategy changeTaskStatusStrategy = new ChangeTaskStatusStrategy(taskService);
    Strategy addNewTaskStrategy = new AddNewTaskStrategy(taskService);
    Strategy removeTaskStrategy = new RemoveTaskStrategy(taskService);
    Strategy editTaskStrategy = new EditTaskStrategy(taskService);
    Strategy addNewUserStrategy = new AddNewUserStrategy(userService);
    Strategy removeUserStrategy = new RemoveUserStrategy(userService);
    Strategy cleanAllTaskTrackerStrategy = new CleanAllTaskTrackerStrategy(taskService);
    Strategy findByMaxTasksCountStrategy = new FindByMaxTasksCountStrategy(userInfoService);


    private final StrategyService strategyService = new StrategyService(stopApplicationStrategy, printAllTasksStrategy,
            printAllFilterTasksStrategy, changeTaskStatusStrategy, addNewTaskStrategy, removeTaskStrategy,
            editTaskStrategy, addNewUserStrategy, removeUserStrategy, cleanAllTaskTrackerStrategy, findByMaxTasksCountStrategy
    );

    @Nested
    @DisplayName("Negative")
    class Negative {

        @Test
        @DisplayName("Test invalid input command")
        void getCommand_InvalidInput_ThrowInputMismatchException() {
            assertEquals("Incorrect input values", strategyService.executeCommand("anyString"));
        }
    }


    @Nested
    @DisplayName("Positive")
    class Positive {

        @Test
        @DisplayName("Test valid input command: printall_withoutfilter")
        void getCommand_ValidInput_PrintAllWithoutFilter() {
            assertEquals("""
                            Задание: id = 4; Заголовок = 'task4'; Описание = 'task desc4'; Срок выполения = Sun Aug 28 00:00:00 MSK 2022; Статус = 'Новое'.
                            Задание: id = 5; Заголовок = 'task5'; Описание = 'task desc5'; Срок выполения = Tue Aug 16 00:00:00 MSK 2022; Статус = 'Новое'.
                            Задание: id = 6; Заголовок = 'task6'; Описание = 'task desc6'; Срок выполения = Sat Aug 06 00:00:00 MSK 2022; Статус = 'Готово'.
                            """,
                    strategyService.executeCommand("printall_withoutfilter 3"));
        }

        @Test
        @DisplayName("Test valid input command: printall_withfilter")
        void getCommand_ValidInput_PrintAllWithFilter() {
            assertEquals("""
                            Задание: id = 4; Заголовок = 'task4'; Описание = 'task desc4'; Срок выполения = Sun Aug 28 00:00:00 MSK 2022; Статус = 'Новое'.
                            Задание: id = 5; Заголовок = 'task5'; Описание = 'task desc5'; Срок выполения = Tue Aug 16 00:00:00 MSK 2022; Статус = 'Новое'.
                            """,
                    strategyService.executeCommand("printall_withfilter 3,Новое"));
        }

        @Test
        @DisplayName("Test valid input command: changestatus")
        void getCommand_ValidInput_ChangeTaskStatus() {
            assertEquals("Status task id = 4 changed to: Готово", strategyService.executeCommand("changestatus 4,Готово"));
        }

        @Test
        @DisplayName("Test valid input command: addtask")
        void getCommand_ValidInput_AddTask() {
            assertEquals("Added task: Задание: id = 1; Заголовок = 'task1'; Описание = 'task desc1'; Срок выполения = Sat Sep 03 00:00:00 MSK 2022; Статус = 'Новое'.\n",
                    strategyService.executeCommand("addtask header,desc,3,2022-09-20,2"));
        }

        @Test
        @DisplayName("Test valid input command: removetask")
        void getCommand_ValidInput_RemoveTask() {
            assertEquals("Task removed", strategyService.executeCommand("removetask 4"));
        }

        @Test
        @DisplayName("Test valid input command: edittask")
        void getCommand_ValidInput_EditTask() {
            assertEquals("Task: 4 edited", strategyService.executeCommand("edittask 4,header,desc,2,2022-09-20,2"));
        }

        @Test
        @DisplayName("Test valid input command: adduser")
        void getCommand_ValidInput_AddUser() {
            assertEquals("New user: Andy saved", strategyService.executeCommand("adduser Andy,Surname,Patronymic,Login,Email,Password,1"));
        }

        @Test
        @DisplayName("Test valid input command: removeuser")
        void getCommand_ValidInput_RemoveUser() {
            assertEquals("User removed", strategyService.executeCommand("removeuser 4"));
        }

        @Test
        @DisplayName("Test valid input command: cleanall")
        void getCommand_ValidInput_CleanAll() {
            assertEquals("All clean", strategyService.executeCommand("cleanall"));
        }
    }
}