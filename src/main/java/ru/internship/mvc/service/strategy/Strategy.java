package ru.internship.mvc.service.strategy;

@FunctionalInterface
public interface Strategy {
    String execute(String... args);
}
