package ru.internship.mvc.dto;

import lombok.Data;

import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.User;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserFindMaxTasksDTO {
    private Long id;
    private String name;
    private List<Task> tasks;

    public static UserFindMaxTasksDTO mapToUserDto(List<Tuple> tuples) {

        if (tuples.size() == 0) {
            throw new EntityNotFoundException("User and tasks not found");
        }
        User user = tuples.get(0).get(1, User.class);
        List<Task> taskList = new ArrayList<>();
        for(Tuple tuple : tuples) {
            taskList.add(tuple.get(0, Task.class));
        }

        UserFindMaxTasksDTO dto = new UserFindMaxTasksDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setTasks(taskList);

        return dto;
    }
}
