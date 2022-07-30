package ru.internship.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.internship.mvc.dto.AppUserDto;
import ru.internship.mvc.dto.InputUserDto;
import ru.internship.mvc.model.Project;
import ru.internship.mvc.model.Role;
import ru.internship.mvc.model.User;
import ru.internship.mvc.repo.ProjectRepo;
import ru.internship.mvc.repo.RoleRepo;
import ru.internship.mvc.repo.UserRepo;
import ru.internship.mvc.util.UserMapper;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final ProjectRepo projectRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User addNewUser(Long idProject, InputUserDto inputUser) {
        Project project = projectRepo.findById(idProject)
                .orElseThrow(() -> new EntityNotFoundException("Project with this id doesn't exist"));
        User user = new User();
        UserMapper.DtoToEntity(user, inputUser);
        List<Project> projectList = new ArrayList<>();
        projectList.add(project);
        user.setProjects(projectList);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        List<Role> roles = roleRepo.findAllById(inputUser.getRoles());
        if (roles.size() == 0) {
            throw new EntityNotFoundException("Roles with these ids don't exist");
        }
        user.setRoles(roles);
        return userRepo.save(user);
    }

    public User updateUser(Long id, InputUserDto updatedUser) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with this id doesn't exist"));
        UserMapper.DtoToEntity(user, updatedUser);
        user.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
        List<Role> roles = roleRepo.findAllById(updatedUser.getRoles());
        if (roles.size() == 0) {
            throw new EntityNotFoundException("Roles with these ids don't exist");
        }
        user.setRoles(roles);
        return userRepo.save(user);
    }

    public String removeUser(Long id) {
        userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with this id doesn't exist"));
        userRepo.deleteById(id);
        return "User: " + id + " deleted";
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with this name doesn't exist"));
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .toList();
        return new AppUserDto(user.getName(), user.getPassword(), authorities);
    }
}
