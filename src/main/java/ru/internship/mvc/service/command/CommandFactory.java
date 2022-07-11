package ru.internship.mvc.service.command;

@FunctionalInterface
public interface CommandFactory {
    Command createCommand(String[] args);
}
