package ru.internship.mvc.util;

import ru.internship.mvc.dto.InputTaskDto;
import ru.internship.mvc.model.Task;

public class TaskMapper {

    public static void DtoToEntity(Task task, InputTaskDto inputTaskDto) {
        task.setHeader(inputTaskDto.getHeader());
        task.setDescription(inputTaskDto.getDescription());
        task.setDeadline(inputTaskDto.getDeadline());
        task.setStatus(inputTaskDto.getStatus());
    }
}
