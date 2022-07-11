package ru.internship.mvc.service.command;

public interface Command extends CommandFactory {
    void execute();
}
