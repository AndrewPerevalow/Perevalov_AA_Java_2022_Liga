package ru.internship.mvc.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.service.UserTracker;

@Service("adduser")
public class AddNewUserStrategy implements Strategy {

    private final UserTracker userTracker;

    @Autowired
    public AddNewUserStrategy(UserTracker userTracker) {
        this.userTracker = userTracker;
    }

    @Override
    public String execute(String...args) {
        return userTracker.addNewUser(args[0]);
    }
}