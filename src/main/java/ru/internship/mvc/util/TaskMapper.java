package ru.internship.mvc.util;

import ru.internship.mvc.dto.input.InputTaskDto;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.enums.Status;

import java.util.InputMismatchException;

public class TaskMapper {

    public static void DtoToEntity(Task task, InputTaskDto inputTaskDto) {
        task.setHeader(inputTaskDto.getHeader());
        task.setDescription(inputTaskDto.getDescription());
        task.setDeadline(inputTaskDto.getDeadline());
        if (validStatus(inputTaskDto.getStatus())) {
            task.setStatus(inputTaskDto.getStatus());
        } else {
            throw new InputMismatchException("Incorrect input status");
        }
    }

    private static boolean validStatus(String status) {
        return status.equals(Status.DEFAULT_STATUS.getStatus()) ||
                status.equals(Status.WORK_STATUS.getStatus()) ||
                status.equals(Status.COMPLETE_STATUS.getStatus());
    }
}
