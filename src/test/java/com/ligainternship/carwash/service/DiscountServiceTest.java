package com.ligainternship.carwash.service;

import com.ligainternship.carwash.dto.request.discount.UpdateDiscountDto;
import com.ligainternship.carwash.exception.DiscountNotFoundException;
import com.ligainternship.carwash.mapper.discount.UpdateDiscountMapper;
import com.ligainternship.carwash.model.entitiy.Discount;
import com.ligainternship.carwash.repo.DiscountRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@DisplayName("Discount service test")
class DiscountServiceTest {

    DiscountService discountService;
    @Mock
    DiscountRepo discountRepo;

    Discount discountMin;
    Discount discountMax;
    UpdateDiscountDto updateDiscountDto;

    @BeforeEach
    void setUp() {

        discountMin = new Discount(
                1L,
                "min",
                5.0
        );
        discountMax = new Discount(
                2L,
                "max",
                15.0
        );
        updateDiscountDto = new UpdateDiscountDto(
                "min",
                10.0
        );

        MockitoAnnotations.openMocks(this);

        Mockito.when(discountRepo.findByName("min")).thenReturn(Optional.of(discountMin));
        Mockito.when(discountRepo.findByName("max")).thenReturn(Optional.of(discountMax));

        Mockito.when(discountRepo.save(any(Discount.class))).thenReturn(discountMin);
        discountService = new DiscountService(discountRepo);
    }

    @Nested
    @DisplayName("Negative")
    class Negative {

        @Test
        @DisplayName("Test discount name doesn't exist")
        void findByName_IncorrectName_ThrowDiscountNotFoundException() {
            Throwable exception = assertThrows(DiscountNotFoundException.class, () -> discountService.findByName(anyString()));
            assertEquals("Discount with this name not found", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Positive")
    class Positive {

        @Test
        @DisplayName("Test discount min name is exist")
        void findByName_CorrectName_ReturnDiscountMin() {
            assertEquals(discountMin, discountService.findByName("min"));
        }

        @Test
        @DisplayName("Test discount max name is exist")
        void findByName_CorrectName_ReturnDiscountMax() {
            assertEquals(discountMax, discountService.findByName("max"));
        }

        @Test
        @DisplayName("Test update discount")
        void update_CorrectInput_ReturnUpdateDiscount() {
            assertEquals(10.0, discountService.update(updateDiscountDto).getValue());
            Mockito.verify(discountRepo, Mockito.times(1)).save(discountMin);
        }
    }


}