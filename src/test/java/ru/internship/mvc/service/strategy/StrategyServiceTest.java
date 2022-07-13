package ru.internship.mvc.service.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.MockitoAnnotations;
import ru.internship.mvc.service.TaskTrackerImpl;
import ru.internship.mvc.service.TaskTrackerInfoImpl;
import ru.internship.mvc.service.UserTrackerImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

class StrategyServiceTest {

    @Mock
    TaskTrackerInfoImpl taskTrackerInfo;
    @Mock
    TaskTrackerImpl taskTracker;
    @Mock
    UserTrackerImpl userTracker;

    {
        MockitoAnnotations.openMocks(this);
        Mockito.when(taskTrackerInfo.printAllTasksForUsers(anyInt())).thenReturn("All tasks");
        Mockito.when(taskTrackerInfo.filterAllTasksForUsersByStatus(anyInt(),anyString())).thenReturn("All filter tasks");
        Mockito.when(taskTracker.changeTaskStatus(anyInt(),anyString())).thenReturn("Status changed");
        Mockito.when(taskTracker.addNewTask(anyString(),anyString(),anyInt(),any())).thenReturn("Task added");
        Mockito.when(taskTracker.removeTask(anyInt())).thenReturn("Task removed");
        Mockito.when(taskTracker.editTask(anyInt(),anyString(),anyString(),anyInt(),any())).thenReturn("Task edited");
        Mockito.when(userTracker.addNewUser(anyString())).thenReturn("User added");
        Mockito.when(userTracker.removeUser(anyInt())).thenReturn("User removed");
        Mockito.when(taskTracker.cleanAllTaskTracker()).thenReturn("All clean");
    }


    Strategy stopApplicationStrategy = new StopApplicationStrategy(Mockito.mock(TaskTrackerImpl.class));
    Strategy printAllTasksStrategy = new PrintAllTaskTrackerImplStrategy(taskTrackerInfo);
    Strategy printAllFilterTasksStrategy = new FilterAllTasksForUsersByStatusStrategy(taskTrackerInfo);
    Strategy changeTaskStatusStrategy = new ChangeTaskStatusStrategy(taskTracker);
    Strategy addNewTaskStrategy = new AddNewTaskStrategy(taskTracker);
    Strategy removeTaskStrategy = new RemoveTaskStrategy(taskTracker);
    Strategy editTaskStrategy = new EditTaskStrategy(taskTracker);
    Strategy addNewUserStrategy = new AddNewUserStrategy(userTracker);
    Strategy removeUserStrategy = new RemoveUserStrategy(userTracker);
    Strategy cleanAllTaskTrackerStrategy = new CleanAllTaskTrackerStrategy(taskTracker);


    private final StrategyService strategyService = new StrategyService(stopApplicationStrategy, printAllTasksStrategy,
            printAllFilterTasksStrategy, changeTaskStatusStrategy, addNewTaskStrategy, removeTaskStrategy,
            editTaskStrategy, addNewUserStrategy, removeUserStrategy, cleanAllTaskTrackerStrategy
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
            assertEquals("All tasks", strategyService.executeCommand("printall_withoutfilter 4"));
        }

        @Test
        @DisplayName("Test valid input command: printall_withfilter")
        void getCommand_ValidInput_PrintAllWithFilter() {
            assertEquals("All filter tasks", strategyService.executeCommand("printall_withfilter 4,Новое"));
        }

        @Test
        @DisplayName("Test valid input command: changestatus")
        void getCommand_ValidInput_ChangeTaskStatus() {
            assertEquals("Status changed", strategyService.executeCommand("changestatus 4,Готово"));
        }

        @Test
        @DisplayName("Test valid input command: addtask")
        void getCommand_ValidInput_AddTask() {
            assertEquals("Task added", strategyService.executeCommand("addtask header,desc,4,20.09.2022"));
        }

        @Test
        @DisplayName("Test valid input command: removetask")
        void getCommand_ValidInput_RemoveTask() {
            assertEquals("Task removed", strategyService.executeCommand("removetask 4"));
        }

        @Test
        @DisplayName("Test valid input command: edittask")
        void getCommand_ValidInput_EditTask() {
            assertEquals("Task edited", strategyService.executeCommand("edittask 4,header,desc,4,20.09.2022"));
        }

        @Test
        @DisplayName("Test valid input command: adduser")
        void getCommand_ValidInput_AddUser() {
            assertEquals("User added", strategyService.executeCommand("adduser Andy"));
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