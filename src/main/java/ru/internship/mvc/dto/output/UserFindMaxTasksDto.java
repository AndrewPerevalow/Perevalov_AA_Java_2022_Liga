package ru.internship.mvc.dto.output;

import lombok.Getter;
import lombok.Setter;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.User;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserFindMaxTasksDto {
    private Long id;
    private String name;
    private List<Task> tasks;

    public static UserFindMaxTasksDto mapToUserDto(List<Tuple> tuples) {

        if (tuples.isEmpty()) {
            throw new EntityNotFoundException("User and tasks not found");
        }
        User user = tuples.get(0).get(1, User.class);
        List<Task> taskList = new ArrayList<>();
        for(Tuple tuple : tuples) {
            taskList.add(tuple.get(0, Task.class));
        }

        UserFindMaxTasksDto dto = new UserFindMaxTasksDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setTasks(taskList);

        return dto;
    }
}
