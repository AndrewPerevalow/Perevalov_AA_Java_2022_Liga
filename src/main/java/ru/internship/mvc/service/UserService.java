package ru.internship.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.internship.mvc.model.Project;
import ru.internship.mvc.model.User;
import ru.internship.mvc.repo.ProjectRepo;
import ru.internship.mvc.repo.UserRepo;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final ProjectRepo projectRepo;

    public User addNewUser(Long idProject, User user) {
        Project project = projectRepo.findById(idProject)
                .orElseThrow(() -> new EntityNotFoundException("Project with this id doesn't exist"));
        List<Project> projectList = new ArrayList<>();
        projectList.add(project);
        user.setProjects(projectList);
        return userRepo.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with this id doesn't exist"));
        user.setName(updatedUser.getName());
        user.setSurname(updatedUser.getSurname());
        user.setPatronymic(updatedUser.getPatronymic());
        user.setLogin(updatedUser.getLogin());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        return userRepo.save(user);
    }

    public String removeUser(Long id) {
        userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User with this id doesn't exist"));
        userRepo.deleteById(id);
        return "User: " + id + " deleted";
    }
}
