package ru.internship.mvc.util;

import java.util.InputMismatchException;

public class ParseCommand {

    private static final int INPUT_LENGTH = 2;

    public static String getCommand(String input) {
        if (input == null || input.equals("")) {
            throw new InputMismatchException("Incorrect input values");
        }
        String[] splitInput = input.split(" ", 2);
        return splitInput[0];
    }

    public static String[] getArgs(String input) {
        if (input == null || input.equals("")) {
            throw new InputMismatchException("Incorrect input values");
        }
        String[] splitInput = input.split(" ", 2);
        if (splitInput.length >= INPUT_LENGTH) {
            return splitInput[1].split(",");
        }
        return new String[0];
    }
}
