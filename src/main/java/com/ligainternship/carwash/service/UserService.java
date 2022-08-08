package com.ligainternship.carwash.service;

import com.ligainternship.carwash.dto.request.user.UpdateUserRoleDto;
import com.ligainternship.carwash.dto.response.user.UserRoleDto;
import com.ligainternship.carwash.exception.UserNotFoundException;
import com.ligainternship.carwash.mapper.user.UpdateUserRoleMapper;
import com.ligainternship.carwash.model.entitiy.Role;
import com.ligainternship.carwash.model.entitiy.User;
import com.ligainternship.carwash.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    private final RoleService roleService;
    private final UpdateUserRoleMapper updateUserRoleMapper;

    @Transactional(readOnly = true)
    public User findById(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            String message = "User with this id not found";
            log.error(message);
            throw new UserNotFoundException(message);
        }
        return optionalUser.get();
    }

    public UserRoleDto updateRole(UpdateUserRoleDto updateUserRoleDto) {
        User user = findById(updateUserRoleDto.getUserId());
        List<Role> roles = user.getRoles();
        Role role = roleService.findById(updateUserRoleDto.getRoleId());
        if (!roles.contains(role)) {
            roles.add(role);
        }
        user.setRoles(roles);
        userRepo.save(user);
        return updateUserRoleMapper.entityToDto(user);
    }
}
