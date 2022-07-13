package ru.internship.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.internship.mvc.model.User;
import ru.internship.mvc.repo.Writer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserTrackerImpl implements UserTracker {

    @Value("${files.user-file}")
    private String userFile;

    private final Writer csvWriter;
    private final Initialize init;

    private Map<Integer, User> usersMap;

    @Autowired
    public UserTrackerImpl(Initialize init, Writer csvWriter) {
        this.init = init;
        this.csvWriter = csvWriter;
    }

    @PostConstruct
    public void firstInit() {
        usersMap = init.getUsersMap();
    }

    public Map<Integer, User> getUsersMap() {
        return usersMap;
    }

    public void setUsersMap(Map<Integer, User> usersMap) {
        this.usersMap = usersMap;
    }

    @Override
    public String addNewUser(String userName) {
        if (isNameValid(userName)) {
            usersMap.put(usersMap.size() + 1, new User(usersMap.size() + 1, userName, new ArrayList<>()));
            csvWriter.writeUsers(userFile, usersMap);
            return "New user added";
        } else {
            throw new InputMismatchException("Incorrect user name");
        }
    }

    @Override
    public String removeUser(int idUser) {
        if (usersMap.containsKey(idUser)) {
            usersMap.remove(idUser);
            csvWriter.writeUsers(userFile, usersMap);
            return "User deleted";
        } else {
            throw new InputMismatchException("User with this id doesn't exist");
        }
    }

    private boolean isNameValid(String userName) {
        String regex = "[A-Za-zА-Яа-я]{3,29}";
        Pattern pattern = Pattern.compile(regex);
        if (userName == null) return false;
        if (userName.trim().length() == 0) return false;
        Matcher matcher = pattern.matcher(userName);
        return matcher.matches();
    }
}
