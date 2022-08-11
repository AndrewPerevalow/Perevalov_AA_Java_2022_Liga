package com.ligainternship.carwash.service;

import com.ligainternship.carwash.dto.request.booking.CancelBookingDto;
import com.ligainternship.carwash.dto.request.booking.CreateBookingDto;
import com.ligainternship.carwash.dto.request.booking.UpdateBookingDto;
import com.ligainternship.carwash.dto.request.discount.CreateDiscountDto;
import com.ligainternship.carwash.exception.BookingNotFoundException;
import com.ligainternship.carwash.mapper.booking.CreateBookingMapper;
import com.ligainternship.carwash.model.entitiy.Booking;
import com.ligainternship.carwash.model.entitiy.Box;
import com.ligainternship.carwash.model.entitiy.Operation;
import com.ligainternship.carwash.model.entitiy.User;
import com.ligainternship.carwash.model.enums.Status;
import com.ligainternship.carwash.repo.BookingRepo;
import com.ligainternship.carwash.service.filter.FilterBookingByBoxIdAndDate;
import com.ligainternship.carwash.service.filter.FilterBookingByDate;
import com.ligainternship.carwash.service.filter.FilterBookingByUserAndStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@DisplayName("Booking service test")
class BookingServiceTest {

    BookingService bookingService;
    @Mock
    BookingRepo bookingRepo;
    @Mock
    BoxService boxService;
    @Mock
    UserService userService;
    @Mock
    OperationService operationService;
    @Mock
    CreateBookingMapper createBookingMapper;
    @Mock
    FilterBookingByBoxIdAndDate filterBookingByBoxIdAndDate;
    @Mock
    FilterBookingByDate filterBookingByDate;
    @Mock
    FilterBookingByUserAndStatus filterBookingByUserAndStatus;

    Booking booking;
    List<Booking> bookings;
    Page<Booking> bookingPage;
    Pageable pageable;
    List<Operation> operations;
    CreateBookingDto createBookingDto;
    Box box;
    Booking notActiveBooking;
    UpdateBookingDto updateBookingDto;
    CancelBookingDto cancelBookingDto;
    Booking actualBookingToComplete;
    CreateDiscountDto createDiscountDto;

    @BeforeEach
    void setUp() {

        booking = new Booking(
                1L,
                LocalDate.now(),
                LocalTime.of(22,0),
                LocalTime.of(22,30),
                "Active",
                0d,
                750d,
                false,
                new ArrayList<>(),
                new User(),
                new Box()
        );
        bookings = new ArrayList<>(List.of(booking));
        bookingPage = new PageImpl<>(bookings);
        pageable = PageRequest.of(0,4);
        Specification<Booking> specification = (root, query, criteriaBuilder) -> null;
        operations = new ArrayList<>();
        createBookingDto = new CreateBookingDto(
                "date",
                "startTime",
                new ArrayList<>(),
                1L
        );
        box = new Box(
                1L,
                "name",
                1.1,
                LocalTime.of(8,0),
                LocalTime.of(20,0),
                new User(),
                new ArrayList<>()
        );
        notActiveBooking = new Booking(
                2L,
                LocalDate.now(),
                LocalTime.of(22,0),
                LocalTime.of(22,30),
                "Cancel",
                0d,
                750d,
                false,
                new ArrayList<>(),
                new User(),
                new Box()
        );
        updateBookingDto = new UpdateBookingDto(
                "2022-08-29",
                "22:00",
                new ArrayList<>(),
                false
        );
        cancelBookingDto = new CancelBookingDto(
                1L,
                Status.CANCEL.getStatus()
        );
        actualBookingToComplete = new Booking(
                3L,
                LocalDate.now(),
                LocalTime.now().plusMinutes(5),
                LocalTime.now().plusMinutes(30),
                Status.ACTIVE.getStatus(),
                10d,
                750d,
                false,
                new ArrayList<>(),
                new User(),
                new Box()
        );
        createDiscountDto = new CreateDiscountDto(
                10.0,
                1L
        );

        MockitoAnnotations.openMocks(this);

        Mockito.when(bookingRepo.findById(1L)).thenReturn(Optional.of(booking));

        Mockito.when(boxService.findById(anyLong())).thenReturn(new Box());
        Mockito.when(filterBookingByBoxIdAndDate.getSpec(new Box(), LocalDate.now(), LocalTime.of(22,0))).thenReturn(specification);
        Mockito.when(bookingRepo.findAll(any(Specification.class) , any(Pageable.class))).thenReturn(bookingPage);

        Mockito.when(filterBookingByDate.getSpec(LocalDate.now().minusDays(1), LocalDate.now())).thenReturn(specification);
        Mockito.when(bookingRepo.findAll(any(Specification.class))).thenReturn(bookings);

        Mockito.when(userService.findById(anyLong())).thenReturn(new User());
        Mockito.when(filterBookingByUserAndStatus.getSpec(new User(), "status")).thenReturn(specification);

        Mockito.when(bookingRepo.findAllByUserAndStatus(new User(), Status.COMPLETE.getStatus())).thenReturn(bookings);

        Mockito.when(createBookingMapper.dtoToEntity(any(CreateBookingDto.class))).thenReturn(booking);
        Mockito.when(boxService.findByDateAndOperations(any(LocalDate.class), any(LocalTime.class), anyInt())).thenReturn(box);
        Mockito.when(bookingRepo.save(any(Booking.class))).thenReturn(booking);

        Mockito.when(bookingRepo.findById(2L)).thenReturn(Optional.of(notActiveBooking));

        Mockito.when(bookingRepo.findById(3L)).thenReturn(Optional.of(actualBookingToComplete));

        bookingService = new BookingService(bookingRepo, boxService, userService, operationService, createBookingMapper,
                filterBookingByBoxIdAndDate, filterBookingByDate, filterBookingByUserAndStatus);
    }

    @Nested
    @DisplayName("Negative")
    class Negative {

        @Test
        @DisplayName("Test booking id doesn't exist")
        void findById_IncorrectId_ThrowBookingNotFoundException() {
            Throwable exception = assertThrows(BookingNotFoundException.class, () -> bookingService.findById(anyLong()));
            assertEquals("Booking with this id not found", exception.getMessage());
        }

        @Test
        @DisplayName("Test not find bookings by box and date")
        void findByBoxIdAndDate_CorrectInput_ThrowBookingNotFoundException() {
            Mockito.when(bookingRepo.findAll(any(Specification.class) , any(Pageable.class))).thenReturn(Page.empty());
            Throwable exception = assertThrows(BookingNotFoundException.class,
                    () -> bookingService.findByBoxIdAndDate(1L, LocalDate.now(), LocalTime.of(22,0), pageable));
            assertEquals(String.format("Booking for box id %d on date %s and time %s not found", 1L, LocalDate.now(), LocalTime.of(22,0)),
                    exception.getMessage()
            );
        }

        @Test
        @DisplayName("Test not find sum total price bookings by date")
        void findSumTotalPriceByDate_CorrectInput_ThrowBookingNotFoundException() {
            Mockito.when(bookingRepo.findAll(any(Specification.class))).thenReturn(Collections.emptyList());
            Throwable exception = assertThrows(BookingNotFoundException.class,
                    () -> bookingService.findSumTotalPriceByDate(LocalDate.now().minusDays(1), LocalDate.now()));
            assertEquals(String.format("Bookings from %s to %s not found", LocalDate.now().minusDays(1), LocalDate.now()),
                    exception.getMessage()
            );
        }

        @Test
        @DisplayName("Test not find bookings by user and status")
        void findAllByUserIdAndStatus_CorrectInput_ThrowBookingNotFoundException() {
            Mockito.when(bookingRepo.findAll(any(Specification.class) , any(Pageable.class))).thenReturn(Page.empty());
            Throwable exception = assertThrows(BookingNotFoundException.class,
                    () -> bookingService.findAllByUserIdAndStatus(1L, "status", pageable));
            assertEquals(String.format("Booking not found for user: %s", 1L), exception.getMessage());
        }

        @Test
        @DisplayName("Test not find operations by user and 'Complete' status")
        void findAllByUserAndCompleteStatus_CorrectInput_ThrowBookingNotFoundException() {
            Mockito.when(bookingRepo.findAllByUserAndStatus(new User(), Status.COMPLETE.getStatus())).thenReturn(Collections.emptyList());
            Throwable exception = assertThrows(BookingNotFoundException.class, () -> bookingService.findAllByUserAndCompleteStatus(1L));
            assertEquals(String.format("Booking not found for user: %s", 1L), exception.getMessage());
        }

        @Test
        @DisplayName("Test update booking with 'Cancel' status")
        void update_IncorrectStatus_ThrowBookingNotFoundException() {
            Throwable exception = assertThrows(BookingNotFoundException.class, () -> bookingService.update(2L, updateBookingDto));
            assertEquals("Booking with this id is cancel or complete", exception.getMessage());
        }

        @Test
        @DisplayName("Test set complete booking when user come at the right time")
        void complete_CorrectInput_ThrowBookingNotFoundException() {
            Throwable exception = assertThrows(BookingNotFoundException.class, () -> bookingService.complete(1L));
            assertEquals("User try to check in too early", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Positive")
    class Positive {

        @Test
        @DisplayName("Test booking id is exist")
        void findById_CorrectId_ReturnBooking() {
            assertEquals(booking, bookingService.findById(1L));
        }

        @Test
        @DisplayName("Test find bookings by box and date")
        void findByBoxIdAndDate_CorrectInput_ReturnBookings() {
            assertEquals(bookingPage, bookingService.findByBoxIdAndDate(1L, LocalDate.now(), LocalTime.of(22,0), pageable));
        }

        @Test
        @DisplayName("Test find sum total price bookings by date")
        void findSumTotalPriceByDate_CorrectInput_ReturnBookings() {
            assertEquals(750d, bookingService.findSumTotalPriceByDate(LocalDate.now().minusDays(1), LocalDate.now()));
        }

        @Test
        @DisplayName("Test find bookings by user and status")
        void findAllByUserIdAndStatus_CorrectInput_ReturnBookings() {
            assertEquals(bookingPage, bookingService.findAllByUserIdAndStatus(1L, "status", pageable));
        }

        @Test
        @DisplayName("Test find operations by user and 'Complete' status")
        void findAllByUserAndCompleteStatus_CorrectInput_ReturnOperations() {
            assertEquals(operations, bookingService.findAllByUserAndCompleteStatus(1L));
        }

        @Test
        @DisplayName("Test create booking")
        void create_CorrectInput_ReturnBooking() {
            assertEquals(booking, bookingService.create(createBookingDto));
            Mockito.verify(bookingRepo, Mockito.times(1)).save(booking);
        }

        @Test
        @DisplayName("Test update booking")
        void update_CorrectInput_ReturnBooking() {
            assertEquals(booking, bookingService.update(1L, updateBookingDto));
            Mockito.verify(bookingRepo, Mockito.times(1)).save(booking);
        }

        @Test
        @DisplayName("Test cancel booking")
        void cancel_CorrectInput_ReturnBooking() {
            assertEquals(booking, bookingService.cancel(cancelBookingDto));
            assertEquals(Status.CANCEL.getStatus(), bookingService.cancel(cancelBookingDto).getStatus());
            Mockito.verify(bookingRepo, Mockito.times(2)).save(booking);
        }

        @Test
        @DisplayName("Test set complete booking when user come at the right time")
        void complete_CorrectInput_ReturnBooking() {
            Mockito.when(bookingRepo.save(any(Booking.class))).thenReturn(actualBookingToComplete);
            assertEquals(actualBookingToComplete, bookingService.complete(3L));
            assertEquals(Status.COMPLETE.getStatus(), bookingService.complete(3L).getStatus());
            assertTrue(bookingService.complete(3L).isUserIsCome());
            Mockito.verify(bookingRepo, Mockito.times(3)).save(actualBookingToComplete);
        }

        @Test
        @DisplayName("Test set discount to booking")
        void createDiscount_CorrectInput_ReturnBooking() {
            assertEquals(booking, bookingService.createDiscount(createDiscountDto));
            assertEquals(10.0, bookingService.createDiscount(createDiscountDto).getDiscount());
            Mockito.verify(bookingRepo, Mockito.times(2)).save(booking);
        }

        @Test
        @DisplayName("Test delete discount in booking")
        void deleteDiscount_CorrectInput_ReturnBooking() {
            Mockito.when(bookingRepo.save(any(Booking.class))).thenReturn(actualBookingToComplete);
            assertEquals(actualBookingToComplete, bookingService.deleteDiscount(3L));
            assertEquals(0d, bookingService.deleteDiscount(3L).getDiscount());
            Mockito.verify(bookingRepo, Mockito.times(2)).save(actualBookingToComplete);
        }
    }
}