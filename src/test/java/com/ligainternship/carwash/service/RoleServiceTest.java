package com.ligainternship.carwash.service;

import com.ligainternship.carwash.exception.RoleNotFoundException;
import com.ligainternship.carwash.model.entitiy.Role;
import com.ligainternship.carwash.model.enums.Roles;
import com.ligainternship.carwash.repo.RoleRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;

@DisplayName("Role service test")
class RoleServiceTest {

    RoleService roleService;
    @Mock
    RoleRepo roleRepo;

    Role userRole;
    Role operatorRole;
    Role adminRole;

    @BeforeEach
    void setUp() {
        userRole = new Role(1L, Roles.USER.getRole(), new ArrayList<>());
        operatorRole = new Role(2L, Roles.OPERATOR.getRole(), new ArrayList<>());
        adminRole = new Role(3L, Roles.ADMIN.getRole(), new ArrayList<>());

        MockitoAnnotations.openMocks(this);
        Mockito.when(roleRepo.findById(1L)).thenReturn(Optional.of(userRole));
        Mockito.when(roleRepo.findById(2L)).thenReturn(Optional.of(operatorRole));
        Mockito.when(roleRepo.findById(3L)).thenReturn(Optional.of(adminRole));

        roleService = new RoleService(roleRepo);
    }

    @Nested
    @DisplayName("Negative")
    class Negative {

        @Test
        @DisplayName("Test role id doesn't exist")
        void findById_IncorrectId_ThrowRoleNotFoundException() {
            Throwable exception = assertThrows(RoleNotFoundException.class, () -> roleService.findById(anyLong()));
            assertEquals("Roles with this id not found", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Positive")
    class Positive {

        @Test
        @DisplayName("Test role USER is exist")
        void findById_CorrectId_ReturnUserRole() {
            assertEquals(userRole, roleService.findById(1L));
        }

        @Test
        @DisplayName("Test role OPERATOR is exist")
        void findById_CorrectId_ReturnOperatorRole() {
            assertEquals(operatorRole, roleService.findById(2L));
        }

        @Test
        @DisplayName("Test role ADMIN is exist")
        void findById_CorrectId_ReturnAdminRole() {
            assertEquals(adminRole, roleService.findById(3L));
        }
    }
}