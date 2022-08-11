package com.ligainternship.carwash.service;

import com.ligainternship.carwash.dto.request.user.CreateUserDto;
import com.ligainternship.carwash.dto.request.user.UpdateUserRoleDto;
import com.ligainternship.carwash.exception.UserNotFoundException;
import com.ligainternship.carwash.mapper.user.CreateUserMapper;
import com.ligainternship.carwash.mapper.user.UpdateUserRoleMapper;
import com.ligainternship.carwash.model.entitiy.Booking;
import com.ligainternship.carwash.model.entitiy.Role;
import com.ligainternship.carwash.model.entitiy.User;
import com.ligainternship.carwash.model.enums.Roles;
import com.ligainternship.carwash.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@DisplayName("User service test")
class UserServiceTest {

    UserService userService;
    @Mock
    UserRepo userRepo;
    @Mock
    RoleService roleService;
    @Mock
    UpdateUserRoleMapper updateUserRoleMapper;
    @Mock
    CreateUserMapper createUserMapper;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    Role userRole;
    Role operatorRole;
    Role adminRole;
    Booking booking;
    User user;
    CreateUserDto createUserDto;
    UpdateUserRoleDto updateUserRoleDtoOperator;
    UpdateUserRoleDto updateUserRoleDtoAdmin;


    @BeforeEach
    void setUp() {
        userRole = new Role(1L, Roles.USER.getRole(), new ArrayList<>());
        operatorRole = new Role(2L, Roles.OPERATOR.getRole(), new ArrayList<>());
        adminRole = new Role(3L, Roles.ADMIN.getRole(), new ArrayList<>());
        booking = new Booking();
        user = new User(
                1L,
                "name",
                "surname",
                "login",
                "email",
                "password",
                "89116457383",
                true,
                new ArrayList<>(List.of(userRole)),
                new ArrayList<>(List.of()),
                new ArrayList<>(List.of(booking))
        );
        createUserDto = new CreateUserDto(
                "name",
                "surname",
                "login",
                "email",
                "password",
                "89116457383"
        );
        updateUserRoleDtoOperator = new UpdateUserRoleDto(1L, 2L);
        updateUserRoleDtoAdmin = new UpdateUserRoleDto(1L, 3L);

        MockitoAnnotations.openMocks(this);

        Mockito.when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(createUserMapper.dtoToEntity(any(CreateUserDto.class))).thenReturn(user);
        Mockito.when(bCryptPasswordEncoder.encode(anyString())).thenReturn(user.getPassword());
        Mockito.when(roleService.findByName(anyString())).thenReturn(userRole);
        Mockito.when(userRepo.save(any(User.class))).thenReturn(user);

        Mockito.when(roleService.findById(2L)).thenReturn(operatorRole);
        Mockito.when(roleService.findById(3L)).thenReturn(adminRole);

        userService = new UserService(userRepo, roleService, createUserMapper, bCryptPasswordEncoder);
    }

    @Nested
    @DisplayName("Negative")
    class Negative {

        @Test
        @DisplayName("Test user id doesn't exist")
        void findById_IncorrectId_ThrowUserNotFoundException() {
            Throwable exception = assertThrows(UserNotFoundException.class, () -> userService.findById(anyLong()));
            assertEquals("User with this id not found", exception.getMessage());
        }

        @Test
        @DisplayName("Test delete doesn't exist user")
        void delete_InCorrectId_ThrowUserNotFoundException() {
            Throwable exception = assertThrows(UserNotFoundException.class, () -> userService.delete(anyLong()));
            assertEquals("User with this id not found", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Positive")
    class Positive {

        @Test
        @DisplayName("Test user id is exist")
        void findById_CorrectId_ReturnUser() {
            assertEquals(1L, userService.findById(1L).getId());
            assertEquals("name", userService.findById(1L).getName());
            assertEquals("surname", userService.findById(1L).getSurname());
            assertEquals("login", userService.findById(1L).getLogin());
            assertEquals("email", userService.findById(1L).getEmail());
            assertEquals("password", userService.findById(1L).getPassword());
            assertEquals("89116457383", userService.findById(1L).getPhoneNumber());
            assertTrue(userService.findById(1L).isExist());
            assertIterableEquals(List.of(userRole), userService.findById(1L).getRoles());
            assertIterableEquals(List.of(), userService.findById(1L).getBoxes());
            assertIterableEquals(List.of(booking), userService.findById(1L).getBookings());
        }

        @Test
        @DisplayName("Test create user entity")
        void create_CorrectInput_ReturnUser() {
            assertEquals(1L, userService.create(createUserDto).getId());
            assertEquals("name", userService.create(createUserDto).getName());
            assertEquals("surname", userService.create(createUserDto).getSurname());
            assertEquals("login", userService.create(createUserDto).getLogin());
            assertEquals("email", userService.create(createUserDto).getEmail());
            assertEquals("89116457383", userService.create(createUserDto).getPhoneNumber());
            Mockito.verify(userRepo, Mockito.times(6)).save(user);
        }

        @Test
        @DisplayName("Test update user to OPERATOR role")
        void updateRole_InputOperatorRole_ReturnUser() {
            assertEquals(1L, userService.updateRole(updateUserRoleDtoOperator).getId());
            assertEquals("name", userService.updateRole(updateUserRoleDtoOperator).getName());
            assertIterableEquals(
                    List.of(userRole, operatorRole),
                    userService.updateRole(updateUserRoleDtoOperator).getRoles()
            );
            Mockito.verify(userRepo, Mockito.times(3)).save(user);
        }

        @Test
        @DisplayName("Test update user to ADMIN role")
        void updateRole_InputAdminRole_ReturnUserRoleDto() {
            assertEquals(1L, userService.updateRole(updateUserRoleDtoAdmin).getId());
            assertEquals("name", userService.updateRole(updateUserRoleDtoAdmin).getName());
            assertIterableEquals(
                    List.of(userRole, adminRole),
                    userService.updateRole(updateUserRoleDtoAdmin).getRoles()
            );
            Mockito.verify(userRepo, Mockito.times(3)).save(user);
        }

        @Test
        @DisplayName("Test delete exists user")
        void delete_CorrectId_SetUserNotExist() {
            userService.delete(1L);
            assertFalse(user.isExist());
            Mockito.verify(userRepo, Mockito.times(1)).save(user);
        }
    }
}