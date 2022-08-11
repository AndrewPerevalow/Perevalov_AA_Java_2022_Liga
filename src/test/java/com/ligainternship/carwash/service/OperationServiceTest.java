package com.ligainternship.carwash.service;

import com.ligainternship.carwash.dto.request.operation.CreateOperationDto;
import com.ligainternship.carwash.exception.OperationNotFoundException;
import com.ligainternship.carwash.exception.UserNotFoundException;
import com.ligainternship.carwash.mapper.operation.CreateOperationMapper;
import com.ligainternship.carwash.model.entitiy.Operation;
import com.ligainternship.carwash.repo.OperationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@DisplayName("Operation service test")
class OperationServiceTest {

    OperationService operationService;
    @Mock
    OperationRepo operationRepo;
    @Mock
    CreateOperationMapper createOperationMapper;

    List<Operation> operations;
    Operation operation;
    CreateOperationDto createOperationDto;

    @BeforeEach
    void setUp() {

        operations = new ArrayList<>(List.of(new Operation(), new Operation()));
        operation = new Operation(
                1L,
                "name",
                30,
                300.0,
                new ArrayList<>()
        );
        createOperationDto = new CreateOperationDto(
                "name",
                30,
                300.0
        );

        MockitoAnnotations.openMocks(this);

        Mockito.when(operationRepo.findAllById(List.of(1L, 2L))).thenReturn(operations);

        Mockito.when(operationRepo.save(any(Operation.class))).thenReturn(operation);
        Mockito.when(createOperationMapper.dtoToEntity(any(CreateOperationDto.class))).thenReturn(operation);

        operationService = new OperationService(operationRepo, createOperationMapper);
    }

    @Nested
    @DisplayName("Negative")
    class Negative {

        @Test
        @DisplayName("Test operation ids don't exist")
        void findAllById_IncorrectIds_ThrowOperationNotFoundException() {
            Throwable exception = assertThrows(OperationNotFoundException.class, () -> operationService.findAllById(List.of(anyLong())));
            assertEquals("Operations with this ids not found", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Positive")
    class Positive {

        @Test
        @DisplayName("Test operation ids are exist")
        void findAllById_CorrectIds_ReturnListOperations() {
            assertEquals(operations, operationService.findAllById(List.of(1L, 2L)));
        }

        @Test
        @DisplayName("Test create operation entity")
        void create_CorrectInput_ReturnOperation() {
            assertEquals(operation, operationService.create(createOperationDto));
            Mockito.verify(operationRepo, Mockito.times(1)).save(operation);
        }
    }
}