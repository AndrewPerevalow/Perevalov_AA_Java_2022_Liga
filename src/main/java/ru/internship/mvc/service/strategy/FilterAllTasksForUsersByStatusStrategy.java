package ru.internship.mvc.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskInfoService;

@Service("printall_withfilter")
public class FilterAllTasksForUsersByStatusStrategy implements Strategy {

    private final TaskInfoService taskInfoService;

    @Autowired
    public FilterAllTasksForUsersByStatusStrategy(TaskInfoService taskInfoService) {
        this.taskInfoService = taskInfoService;
    }

    @Override
    public String execute(String... args) {
        Long id = Long.parseLong(args[0]);
        String status = args[1];
        return taskInfoService.filterAllTasksForUsersByStatus(id, status)
                .toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\, ", "");
    }
}
