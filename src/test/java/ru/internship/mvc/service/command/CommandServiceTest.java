package ru.internship.mvc.service.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ru.internship.mvc.service.TaskTrackerImpl;
import ru.internship.mvc.service.TaskTrackerInfoImpl;
import ru.internship.mvc.service.UserTrackerImpl;

import static org.junit.jupiter.api.Assertions.*;

class CommandServiceTest {

    Command stopApplicationCommand = new StopApplicationCommand(Mockito.mock(TaskTrackerImpl.class));
    Command printAllTasksCommand = new PrintAllTaskTrackerImplCommand(Mockito.mock(TaskTrackerInfoImpl.class));
    Command printAllFilterTasksCommand = new FilterAllTasksForUsersByStatusCommand(Mockito.mock(TaskTrackerInfoImpl.class));
    Command changeTaskStatusCommand = new ChangeTaskStatusCommand(Mockito.mock(TaskTrackerImpl.class));
    Command addNewTaskCommand = new AddNewTaskCommand(Mockito.mock(TaskTrackerImpl.class));
    Command removeTaskCommand = new RemoveTaskCommand(Mockito.mock(TaskTrackerImpl.class));
    Command editTaskCommand = new EditTaskCommand(Mockito.mock(TaskTrackerImpl.class));
    Command addNewUserCommand = new AddNewUserCommand(Mockito.mock(UserTrackerImpl.class));
    Command removeUserCommand = new RemoveUserCommand(Mockito.mock(UserTrackerImpl.class));
    Command cleanAllTaskTrackerCommand = new CleanAllTaskTrackerCommand(Mockito.mock(TaskTrackerImpl.class));


    private final CommandService commandService = new CommandService(stopApplicationCommand, printAllTasksCommand,
            printAllFilterTasksCommand, changeTaskStatusCommand, addNewTaskCommand, removeTaskCommand,
            editTaskCommand, addNewUserCommand, removeUserCommand, cleanAllTaskTrackerCommand
    );

    @Nested
    @DisplayName("Negative")
    class Negative {

        @Test
        @DisplayName("Test invalid input command")
        void getCommand_InvalidInput_ThrowInputMismatchException() {
            assertEquals("Incorrect input values", commandService.executeCommand("anyString"));
        }
    }

    @Nested
    @DisplayName("Positive")
    class Positive {

        @Test
        @DisplayName("Test valid input command")
        void getCommand_ValidInput_PrintAllWithoutFilter() {
            assertEquals("Success, let's see result in console", commandService.executeCommand("printall_withoutfilter 4"));
        }
    }
}