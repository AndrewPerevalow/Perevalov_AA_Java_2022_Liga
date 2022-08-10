package com.ligainternship.carwash.service;

import com.ligainternship.carwash.dto.AppUserDto;
import com.ligainternship.carwash.dto.request.user.CreateUserDto;
import com.ligainternship.carwash.dto.request.user.UpdateUserRoleDto;
import com.ligainternship.carwash.dto.response.user.UserDto;
import com.ligainternship.carwash.dto.response.user.UserRoleDto;
import com.ligainternship.carwash.exception.UserNotFoundException;
import com.ligainternship.carwash.mapper.user.CreateUserMapper;
import com.ligainternship.carwash.mapper.user.UpdateUserRoleMapper;
import com.ligainternship.carwash.model.entitiy.Role;
import com.ligainternship.carwash.model.entitiy.User;
import com.ligainternship.carwash.model.enums.Roles;
import com.ligainternship.carwash.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final RoleService roleService;
    private final UpdateUserRoleMapper updateUserRoleMapper;
    private final CreateUserMapper createUserMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @Transactional(readOnly = true)
    public Optional<User> findByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    public UserDto create(CreateUserDto createUserDto) {
        User user = createUserMapper.dtoToEntity(createUserDto);
        user.setPassword(bCryptPasswordEncoder.encode(createUserDto.getPassword()));
        Role role = roleService.findByName(Roles.USER.getRole());
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        user.setExist(true);
        userRepo.save(user);
        return createUserMapper.entityToDto(user);
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

    public void delete(Long id) {
        User user = findById(id);
        user.setExist(false);
        userRepo.save(user);
    }

    @Override
    public AppUserDto loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepo.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User with this name doesn't exist"));
        if (!user.isExist()) {
            String message = "User with this id doesn't exist";
            log.error(message);
            throw new UserNotFoundException(message);
        }
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .toList();
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
        return new AppUserDto(userDetails);
    }
}
