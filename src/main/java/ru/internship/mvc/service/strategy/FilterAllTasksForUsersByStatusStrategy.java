package ru.internship.mvc.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskTrackerInfo;

@Service("printall_withfilter")
public class FilterAllTasksForUsersByStatusStrategy implements Strategy {

    private final TaskTrackerInfo taskTrackerInfo;

    @Autowired
    public FilterAllTasksForUsersByStatusStrategy(TaskTrackerInfo taskTrackerInfo) {
        this.taskTrackerInfo = taskTrackerInfo;
    }

    @Override
    public String execute(String...args) {
        return taskTrackerInfo.filterAllTasksForUsersByStatus(Integer.parseInt(args[0]), args[1]);
    }
}
