package ru.internship.mvc.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskTrackerInfo;

@Service("printall_withoutfilter")
public class PrintAllTaskTrackerImplStrategy implements Strategy {

    private final TaskTrackerInfo taskTrackerInfo;

    @Autowired
    public PrintAllTaskTrackerImplStrategy(TaskTrackerInfo taskTrackerInfo) {
        this.taskTrackerInfo = taskTrackerInfo;
    }

    @Override
    public String execute(String...args) {
        return taskTrackerInfo.printAllTasksForUsers(Integer.parseInt(args[0]));
    }
}
