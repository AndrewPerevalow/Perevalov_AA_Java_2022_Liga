package ru.internship.mvc.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskInfoService;

@Service("printall_withoutfilter")
public class PrintAllTaskTrackerImplStrategy implements Strategy {

    private final TaskInfoService taskInfoService;

    @Autowired
    public PrintAllTaskTrackerImplStrategy(TaskInfoService taskInfoService) {
        this.taskInfoService = taskInfoService;
    }

    @Override
    public String execute(String... args) {
        Long id = Long.parseLong(args[0]);
        return taskInfoService.printAllTasksForUsers(id)
                .toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\, ", "");
    }
}
