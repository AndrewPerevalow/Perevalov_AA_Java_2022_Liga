package ru.internship.mvc.service.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class CommandServiceTest {

    @Mock
    Command stopApplicationCommand;
    @Mock
    Command printAllTasksCommand;
    @Mock
    Command printAllFilterTasksCommand;
    @Mock
    Command changeTaskStatusCommand;
    @Mock
    Command addNewTaskCommand;
    @Mock
    Command removeTaskCommand;
    @Mock
    Command editTaskCommand;
    @Mock
    Command addNewUserCommand;
    @Mock
    Command removeUserCommand;
    @Mock
    Command cleanAllTaskTrackerCommand;


    private final CommandService commandService = new CommandService(stopApplicationCommand, printAllTasksCommand,
            printAllFilterTasksCommand, changeTaskStatusCommand, addNewTaskCommand, removeTaskCommand,
            editTaskCommand, addNewUserCommand, removeUserCommand, cleanAllTaskTrackerCommand
    );

    @BeforeEach
    void setUp() {
//        Mockito.when(commandService.executeCommand(anyString())).
    }

    @Nested
    @DisplayName("Positive")
    class Positive {

        @Test
        @DisplayName("Test invalid input command")
        void getCommand_InvalidInput_ThrowInputMismatchException() {
            assertEquals("Incorrect input values", commandService.executeCommand(anyString()));
        }
    }

    @Nested
    @DisplayName("Negative")
    class Negative {

        @Test
        @DisplayName("Test valid input command")
        void getCommand_ValidInput_PrintAllWithoutFilter() {
            assertEquals("Success, let's see result in console", commandService.executeCommand("printall_withoutfilter 4"));
        }
    }
}