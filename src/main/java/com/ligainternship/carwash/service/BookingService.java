package com.ligainternship.carwash.service;

import com.ligainternship.carwash.dto.request.booking.CancelBookingDto;
import com.ligainternship.carwash.dto.request.booking.CreateBookingDto;
import com.ligainternship.carwash.dto.response.booking.BookingDto;
import com.ligainternship.carwash.exception.BookingNotFoundException;
import com.ligainternship.carwash.mapper.box.CreateBookingMapper;
import com.ligainternship.carwash.model.entitiy.Booking;
import com.ligainternship.carwash.model.entitiy.Box;
import com.ligainternship.carwash.model.entitiy.Operation;
import com.ligainternship.carwash.model.enums.Status;
import com.ligainternship.carwash.repo.BookingRepo;
import com.ligainternship.carwash.service.filter.FilterByBoxIdAndDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookingService {

    private final BookingRepo bookingRepo;
    private final BoxService boxService;
    private final CreateBookingMapper createBookingMapper;
    private final FilterByBoxIdAndDate filterByBoxIdAndDate;

    @Transactional(readOnly = true)
    public Booking findById(Long id) {
        Optional<Booking> optionalBooking = bookingRepo.findById(id);
        if (optionalBooking.isEmpty()) {
            String message = "Booking with this id not found";
            log.error(message);
            throw new BookingNotFoundException(message);
        }
        return optionalBooking.get();
    }

    @Transactional(readOnly = true)
    public Page<BookingDto> findByBoxIdAndDate(Long id, LocalDate date, LocalTime time, Pageable pageable) {
        Box box = boxService.findById(id);
        Specification<Booking> specification = filterByBoxIdAndDate.getSpec(box, date, time);
        Page<Booking> bookings = bookingRepo.findAll(specification, pageable);
        return bookings.map(createBookingMapper::entityToDto);
    }

    public BookingDto create(CreateBookingDto createBookingDto) {
        Booking booking = createBookingMapper.dtoToEntity(createBookingDto);
        int totalLeadTime = booking.getOperations().stream()
                .map(Operation::getLeadTime)
                .mapToInt(Integer::intValue)
                .sum();
        Box box = boxService.findByDateAndOperations(booking.getDate(), booking.getStartTime(), totalLeadTime);
        Double totalPrice = booking.getOperations().stream()
                .map(Operation::getPrice)
                .mapToDouble(Double::doubleValue)
                .sum();
        booking.setBox(box);
        booking.setTotalPrice(totalPrice);
        booking.setStatus(Status.ACTIVE.getStatus());
        LocalTime endTime = booking.getStartTime()
                .plusMinutes((int) Math.ceil(
                        (totalLeadTime * box.getRatio())));
        booking.setEndTime(endTime);
        bookingRepo.save(booking);
        return createBookingMapper.entityToDto(booking);
    }

    public BookingDto cancel(CancelBookingDto cancelBookingDto) {
        Booking booking = findById(cancelBookingDto.getId());
        booking.setStatus(cancelBookingDto.getStatus());
        bookingRepo.save(booking);
        return createBookingMapper.entityToDto(booking);
    }
}
