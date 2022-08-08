package com.ligainternship.carwash.rest;

import com.ligainternship.carwash.dto.request.booking.CancelBookingDto;
import com.ligainternship.carwash.dto.request.booking.CreateBookingDto;
import com.ligainternship.carwash.dto.request.booking.UpdateBookingDto;
import com.ligainternship.carwash.dto.request.discount.CreateDiscountDto;
import com.ligainternship.carwash.dto.response.booking.BookingDto;
import com.ligainternship.carwash.dto.response.booking.TotalSumDto;
import com.ligainternship.carwash.exception.InvalidInputException;
import com.ligainternship.carwash.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/bookings/box/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Page<BookingDto> findByBoxIdAndDate(@PathVariable("id") Long id,
                                               @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("date") LocalDate date,
                                               @DateTimeFormat(pattern = "HH:mm") @RequestParam("time")LocalTime time,
                                               Pageable pageable) {
        return bookingService.findByBoxIdAndDate(id, date, time, pageable);
    }

    @GetMapping("/bookings/total-sum")
    @ResponseStatus(code = HttpStatus.OK)
    public Page<TotalSumDto> findByDate(@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("dateFrom") LocalDate dateFrom,
                                        @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("dateTo") LocalDate dateTo,
                                        Pageable pageable) {
        return bookingService.findSumTotalPriceByDate(dateFrom, dateTo, pageable);
    }

    @GetMapping("/bookings/user/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Page<BookingDto> findByUserAndStatus(@PathVariable("id") Long id, Pageable pageable) {
        return bookingService.findAllByUserIdAndStatus(id, pageable);
    }

    @PostMapping("/bookings")
    @ResponseStatus(code = HttpStatus.OK)
    public BookingDto create(@Valid @RequestBody CreateBookingDto createBookingDto,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new InvalidInputException(errors);
        }
        return bookingService.create(createBookingDto);
    }

    @PutMapping("/bookings/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public BookingDto update(@PathVariable("id") Long id,
                             @Valid @RequestBody UpdateBookingDto updateBookingDto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new InvalidInputException(errors);
        }
        return bookingService.update(id, updateBookingDto);
    }

    @PutMapping("/bookings/create-discount")
    @ResponseStatus(code = HttpStatus.OK)
    public BookingDto createDiscount(@Valid @RequestBody CreateDiscountDto createDiscountDto,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new InvalidInputException(errors);
        }
        return bookingService.createDiscount(createDiscountDto);
    }

    @PutMapping("/bookings/delete-discount/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public BookingDto deleteDiscount(@PathVariable("id") Long id) {
        return bookingService.deleteDiscount(id);
    }

    @PutMapping("/bookings/cancel-booking")
    @ResponseStatus(code = HttpStatus.OK)
    public BookingDto cancel(@Valid @RequestBody CancelBookingDto cancelBookingDto,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new InvalidInputException(errors);
        }
        return bookingService.cancel(cancelBookingDto);
    }

    @PutMapping("/bookings/complete-booking/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public BookingDto complete(@PathVariable("id") Long id) {
        return bookingService.complete(id);
    }


}
