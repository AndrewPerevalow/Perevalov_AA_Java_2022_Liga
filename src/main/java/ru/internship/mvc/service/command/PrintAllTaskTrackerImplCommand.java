package ru.internship.mvc.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.TaskTrackerInfo;

@Service("printall_withoutfilter")
public class PrintAllTaskTrackerImplCommand implements Command {

    private final TaskTrackerInfo taskTrackerInfo;

    private int idUser;

    @Autowired
    public PrintAllTaskTrackerImplCommand(TaskTrackerInfo taskTrackerInfo) {
        this.taskTrackerInfo = taskTrackerInfo;
    }

    @Override
    public void execute() {
        taskTrackerInfo.printAllTasksForUsers(idUser);
    }

    @Override
    public Command createCommand(String[] args) {
        this.idUser = Integer.parseInt(args[0]);
        return this;
    }
}
