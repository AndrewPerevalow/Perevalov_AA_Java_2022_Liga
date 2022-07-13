package ru.internship.mvc.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.UserTracker;

@Service("removeuser")
public class RemoveUserStrategy implements Strategy {

    private final UserTracker userTracker;

    @Autowired
    public RemoveUserStrategy(UserTracker userTracker) {
        this.userTracker = userTracker;
    }

    @Override
    public String execute(String...args) {
        return userTracker.removeUser(Integer.parseInt(args[0]));
    }
}
