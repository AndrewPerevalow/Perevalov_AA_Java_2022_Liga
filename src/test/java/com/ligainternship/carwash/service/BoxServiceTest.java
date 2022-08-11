package com.ligainternship.carwash.service;

import com.ligainternship.carwash.dto.request.box.CreateBoxDto;
import com.ligainternship.carwash.exception.BoxNotFoundException;
import com.ligainternship.carwash.mapper.box.CreateBoxMapper;
import com.ligainternship.carwash.model.entitiy.Box;
import com.ligainternship.carwash.model.entitiy.User;
import com.ligainternship.carwash.model.enums.Roles;
import com.ligainternship.carwash.repo.BoxRepo;
import com.ligainternship.carwash.service.filter.FilterUserByRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@DisplayName("Box service test")
class BoxServiceTest {

    BoxService boxService;
    @Mock
    BoxRepo boxRepo;
    @Mock
    CreateBoxMapper createBoxMapper;
    @Mock
    FilterUserByRole filterUserByRole;

    List<Box> boxes;
    Box box;
    CreateBoxDto createBoxDto;

    @BeforeEach
    void setUp() {
        box = new Box(
                1L,
                "name",
                1.1,
                LocalTime.of(8,0),
                LocalTime.of(20,0),
                new User(),
                new ArrayList<>()
        );
        boxes = new ArrayList<>(List.of(box));
        createBoxDto = new CreateBoxDto(
                "name",
                1.1,
                "8:00",
                "20:00",
                2L
        );

        MockitoAnnotations.openMocks(this);

        Mockito.when(boxRepo.findByDateAndOperations(
                LocalDate.now(), LocalTime.of(13,30).getHour(), LocalTime.of(13,30).getMinute(), 30))
                .thenReturn(boxes);

        Mockito.when(boxRepo.findById(1L)).thenReturn(Optional.of(box));

        Mockito.when(filterUserByRole.findByIdAndRole(1L, Roles.OPERATOR.getRole())).thenReturn(new User());
        Mockito.when(createBoxMapper.dtoToEntity(any(CreateBoxDto.class))).thenReturn(box);
        Mockito.when(boxRepo.save(any(Box.class))).thenReturn(box);

        boxService = new BoxService(boxRepo, createBoxMapper, filterUserByRole);
    }

    @Nested
    @DisplayName("Negative")
    class Negative {

        @Test
        @DisplayName("Test free box not found")
        void findByDateAndOperations_NoFreeBoxesOnThisDateTime_ThrowBoxNotFoundException() {
            Throwable exception = assertThrows(
                    BoxNotFoundException.class, () -> boxService.findByDateAndOperations(
                            LocalDate.now(), LocalTime.of(7, 30), 30)
            );
            assertEquals(String.format("Not found free boxes on date: %s and time: %s", LocalDate.now(), LocalTime.of(7, 30)),
                    exception.getMessage()
            );
        }

        @Test
        @DisplayName("Test box id doesn't exist")
        void findById_IncorrectId_ThrowBoxNotFoundException() {
            Throwable exception = assertThrows(BoxNotFoundException.class, () -> boxService.findById(anyLong()));
            assertEquals("Box with this id not found", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Positive")
    class Positive {

        @Test
        @DisplayName("Test free box found")
        void findByDateAndOperations_CorrectInput_ReturnBox() {
            assertEquals(box, boxService.findByDateAndOperations(LocalDate.now(), LocalTime.of(13, 30), 30));
        }

        @Test
        @DisplayName("Test box id is exist")
        void findById_CorrectId_ReturnBox() {
            assertEquals(box, boxService.findById(1L));
        }

        @Test
        @DisplayName("Test create box")
        void create_CorrectInput_ReturnBox() {
            assertEquals(box, boxService.create(createBoxDto));
            Mockito.verify(boxRepo, Mockito.times(1)).save(box);
        }
    }
}