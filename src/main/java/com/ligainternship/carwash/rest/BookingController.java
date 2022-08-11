package com.ligainternship.carwash.rest;

import com.ligainternship.carwash.dto.request.booking.CancelBookingDto;
import com.ligainternship.carwash.dto.request.booking.CreateBookingDto;
import com.ligainternship.carwash.dto.request.booking.UpdateBookingDto;
import com.ligainternship.carwash.dto.request.discount.CreateDiscountDto;
import com.ligainternship.carwash.dto.response.booking.BookingDto;
import com.ligainternship.carwash.dto.response.booking.TotalSumDto;
import com.ligainternship.carwash.dto.response.operation.OperationDto;
import com.ligainternship.carwash.dto.validate.status.ValidStatus;
import com.ligainternship.carwash.exception.InvalidInputException;
import com.ligainternship.carwash.mapper.booking.CreateBookingMapper;
import com.ligainternship.carwash.mapper.operation.CreateOperationMapper;
import com.ligainternship.carwash.model.entitiy.Booking;
import com.ligainternship.carwash.model.entitiy.Operation;
import com.ligainternship.carwash.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Validated
public class BookingController {

    private final BookingService bookingService;
    private final CreateBookingMapper createBookingMapper;
    private final CreateOperationMapper createOperationMapper;

    @GetMapping("/bookings/box/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR') and @boxService.findById(#id).user.login eq authentication.name")
    public Page<BookingDto> findByBoxIdAndDate(@PathVariable("id") Long id,
                                               @DateTimeFormat(pattern = "yyyy-MM-dd") @Nullable @RequestParam("date") LocalDate date,
                                               @DateTimeFormat(pattern = "HH:mm") @Nullable @RequestParam("time")LocalTime time,
                                               Pageable pageable) {
        Page<Booking> bookings = bookingService.findByBoxIdAndDate(id, date, time, pageable);
        return bookings.map(createBookingMapper::entityToDto);
    }

    @GetMapping("/bookings/total-sum")
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Page<TotalSumDto> findSumTotalPriceByDate(@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("dateFrom") LocalDate dateFrom,
                                                     @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("dateTo") LocalDate dateTo,
                                                     Pageable pageable) {
        Double totalSum = bookingService.findSumTotalPriceByDate(dateFrom, dateTo);
        TotalSumDto totalSumDto = new TotalSumDto();
        totalSumDto.setTotalSum(totalSum);
        return new PageImpl<>(List.of(totalSumDto), pageable, 1);
    }

    @GetMapping("/bookings/user/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or hasAnyRole('OPERATOR','USER') and @userService.findById(#id).login eq authentication.name")
    public Page<BookingDto> findAllByUserIdAndStatus(@PathVariable("id") Long id,
                                                     @RequestParam("status") @ValidStatus @NotEmpty String status,
                                                     Pageable pageable) {
        Page<Booking> bookings = bookingService.findAllByUserIdAndStatus(id, status, pageable);
        return bookings.map(createBookingMapper::entityToDto);
    }


    @GetMapping("bookings/operations/user/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or hasAnyRole('OPERATOR','USER') and @userService.findById(#id).login eq authentication.name")
    public Page<OperationDto> findAllCompleteOperationsByUser(@PathVariable("id") Long id,
                                                              Pageable pageable) {
        List<Operation> operations = bookingService.findAllByUserAndCompleteStatus(id);
        List<OperationDto> listOperationsDto = operations.stream()
                .map(createOperationMapper::entityToDto)
                .toList();
        return new PageImpl<>(listOperationsDto, pageable, 1);
    }

    @PostMapping("/bookings")
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN','OPERATOR') or" +
            " hasRole('USER') and @userService.findById(#createBookingDto.userId).login eq authentication.name")
    public BookingDto create(@Valid @RequestBody CreateBookingDto createBookingDto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new InvalidInputException(errors);
        }
        Booking booking = bookingService.create(createBookingDto);
        return createBookingMapper.entityToDto(booking);
    }

    @PutMapping("/bookings/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or " +
            "hasRole('OPERATOR') and @bookingService.findById(#id).box.user.login eq authentication.name or " +
            "hasRole('USER') and @bookingService.findById(#id).user.login eq authentication.name")
    public BookingDto update(@PathVariable("id") Long id,
                             @Valid @RequestBody UpdateBookingDto updateBookingDto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new InvalidInputException(errors);
        }
        Booking booking = bookingService.update(id, updateBookingDto);
        return createBookingMapper.entityToDto(booking);
    }

    @PutMapping("/bookings/create-discount")
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or " +
            "hasRole('OPERATOR') and @bookingService.findById(#createDiscountDto.bookingId).box.user.login eq authentication.name")
    public BookingDto createDiscount(@Valid @RequestBody CreateDiscountDto createDiscountDto,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new InvalidInputException(errors);
        }
        Booking booking = bookingService.createDiscount(createDiscountDto);
        return createBookingMapper.entityToDto(booking);
    }

    @PutMapping("/bookings/delete-discount/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or " +
            "hasRole('OPERATOR') and @bookingService.findById(#id).box.user.login eq authentication.name")
    public BookingDto deleteDiscount(@PathVariable("id") Long id) {
        Booking booking = bookingService.deleteDiscount(id);
        return createBookingMapper.entityToDto(booking);
    }

    @PutMapping("/bookings/cancel-booking")
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or " +
            "hasRole('OPERATOR') and @bookingService.findById(#cancelBookingDto.id).box.user.login eq authentication.name or " +
            "hasRole('USER') and @bookingService.findById(#cancelBookingDto.id).user.login eq authentication.name")
    public BookingDto cancel(@Valid @RequestBody CancelBookingDto cancelBookingDto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new InvalidInputException(errors);
        }
        Booking booking = bookingService.cancel(cancelBookingDto);
        return createBookingMapper.entityToDto(booking);
    }

    @PutMapping("/bookings/complete-booking/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or " +
            "hasRole('OPERATOR') and @bookingService.findById(#id).box.user.login eq authentication.name or " +
            "hasRole('USER') and @bookingService.findById(#id).user.login eq authentication.name")
    public BookingDto complete(@PathVariable("id") Long id) {
        Booking booking = bookingService.complete(id);
        return createBookingMapper.entityToDto(booking);
    }


}
