package ru.internship.mvc.service.command;

@FunctionalInterface
public interface Command {
    void execute(String... args);
}
