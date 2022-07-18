package ru.internship.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.mvc.model.User;
import ru.internship.mvc.repo.UserRepo;

import javax.persistence.EntityNotFoundException;
import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public String addNewUser(User user) {
        if(isNameValid(user.getName())) {
            userRepo.save(user);
        } else {
            throw new InputMismatchException("Incorrect user name");
        }
        return "New user: " + user.getName() + " saved";
    }

    public String removeUser(Long id) {
        userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User with this id doesn't exist"));
        userRepo.deleteById(id);
        return "User: " + id + " deleted";
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
