package com.ligainternship.carwash.service;

import com.ligainternship.carwash.dto.request.booking.CancelBookingDto;
import com.ligainternship.carwash.dto.request.booking.CreateBookingDto;
import com.ligainternship.carwash.dto.request.booking.UpdateBookingDto;
import com.ligainternship.carwash.dto.request.discount.CreateDiscountDto;
import com.ligainternship.carwash.dto.response.booking.BookingDto;
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
import com.ligainternship.carwash.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookingService {

    private final BookingRepo bookingRepo;
    private final BoxService boxService;
    private final UserService userService;
    private final OperationService operationService;
    private final CreateBookingMapper createBookingMapper;
    private final FilterBookingByBoxIdAndDate filterBookingByBoxIdAndDate;
    private final FilterBookingByDate filterBookingByDate;
    private final FilterBookingByUserAndStatus filterBookingByUserAndStatus;

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
    public Page<Booking> findByBoxIdAndDate(Long id, LocalDate date, LocalTime time, Pageable pageable) {
        Box box = boxService.findById(id);
        Specification<Booking> specification = filterBookingByBoxIdAndDate.getSpec(box, date, time);
        Page<Booking> bookings = bookingRepo.findAll(specification, pageable);
        if (bookings.isEmpty()) {
            String message = String.format("Booking for box id %d on date %s and time %s not found", id, date, time);
            log.error(message);
            throw new BookingNotFoundException(message);
        }
        return bookings;
    }

    @Transactional(readOnly = true)
    public Double findSumTotalPriceByDate(LocalDate fromDate, LocalDate toDate) {
        Specification<Booking> specification = filterBookingByDate.getSpec(fromDate, toDate);
        List<Booking> bookings = bookingRepo.findAll(specification);
        if (bookings.isEmpty()) {
            String message = String.format("Bookings from %s to %s not found", fromDate, toDate);
            log.error(message);
            throw new BookingNotFoundException(message);
        }
        return getTotalSum(bookings);
    }

    @Transactional(readOnly = true)
    public Page<Booking> findAllByUserIdAndStatus(Long id, String status, Pageable pageable) {
        User user = userService.findById(id);
        Specification<Booking> specification = filterBookingByUserAndStatus.getSpec(user, status);
        Page<Booking> bookings = bookingRepo.findAll(specification, pageable);
        if (bookings.isEmpty()) {
            String message = String.format("Booking not found for user: %s",id);
            log.error(message);
            throw new BookingNotFoundException(message);
        }
        return bookings;
    }

    @Transactional(readOnly = true)
    public List<Operation> findAllByUserAndCompleteStatus(Long id) {
        User user = userService.findById(id);
        List<Booking> bookings = bookingRepo.findAllByUserAndStatus(user, Status.COMPLETE.getStatus());
        if (bookings.isEmpty()) {
            String message = String.format("Booking not found for user: %s", id);
            log.error(message);
            throw new BookingNotFoundException(message);
        }
        return getUniqueOperations(bookings);
    }

    public Booking create(CreateBookingDto createBookingDto) {
        Booking booking = createBookingMapper.dtoToEntity(createBookingDto);
        int totalLeadTime = getLeadTime(booking.getOperations());
        Box box = boxService.findByDateAndOperations(booking.getDate(), booking.getStartTime(), totalLeadTime);
        booking.setBox(box);
        Double totalPrice = getPrice(booking.getOperations());
        booking.setTotalPrice(totalPrice);
        booking.setStatus(Status.ACTIVE.getStatus());
        LocalTime endTime = getEndTime(booking.getStartTime(), totalLeadTime, box.getRatio());
        booking.setEndTime(endTime);
        return bookingRepo.save(booking);
    }

    public Booking update(Long id, UpdateBookingDto updateBookingDto) {
        Booking booking = findById(id);
        if (!booking.getStatus().equals(Status.ACTIVE.getStatus())) {
            String message = "Booking with this id is cancel or complete";
            log.error(message);
            throw new BookingNotFoundException(message);
        }
        List<Operation> operations = operationService.findAllById(updateBookingDto.getOperations());
        booking.setOperations(operations);
        Double totalPrice = getPrice(operations);
        booking.setTotalPrice(totalPrice);
        if (updateBookingDto.getDate() != null) {
            LocalDate date = StringUtils.parseDate(updateBookingDto.getDate());
            Box box = boxService.findByDateAndOperations(date, booking.getStartTime(), getLeadTime(operations));
            booking.setDate(date);
            booking.setBox(box);
        }
        if (updateBookingDto.getStartTime() != null) {
            LocalTime startTime = StringUtils.parseTime(updateBookingDto.getStartTime());
            Box box = boxService.findByDateAndOperations(booking.getDate(), startTime, getLeadTime(operations));
            booking.setStartTime(startTime);
            booking.setBox(box);
        }
        if (updateBookingDto.isCancel()) {
            booking.setStatus(Status.CANCEL.getStatus());
        }
        LocalTime endTime = getEndTime(booking.getStartTime(), getLeadTime(operations), booking.getBox().getRatio());
        booking.setEndTime(endTime);
        return bookingRepo.save(booking);
    }

    public Booking cancel(CancelBookingDto cancelBookingDto) {
        Booking booking = findById(cancelBookingDto.getId());
        booking.setStatus(cancelBookingDto.getStatus());
        return bookingRepo.save(booking);
    }

    public Booking complete(Long id) {
        Booking booking = findById(id);
        if (!checkByDate(booking.getDate(), booking.getStartTime())) {
            String message = "User try to check in too early";
            log.error(message);
            throw new BookingNotFoundException(message);
        }
        booking.setStatus(Status.COMPLETE.getStatus());
        booking.setUserIsCome(true);
        return bookingRepo.save(booking);
    }

    public Booking createDiscount(CreateDiscountDto createDiscountDto) {
        Booking booking = findById(createDiscountDto.getBookingId());
        booking.setDiscount(createDiscountDto.getValue());
        Double totalPrice = getPriceWithDiscount(getPrice(booking.getOperations()), createDiscountDto.getValue());
        booking.setTotalPrice(totalPrice);
        return bookingRepo.save(booking);
    }

    public Booking deleteDiscount(Long id) {
        Booking booking = findById(id);
        Double totalPrice = getPrice(booking.getOperations());
        booking.setTotalPrice(totalPrice);
        booking.setDiscount(0d);
        return bookingRepo.save(booking);
    }

    private Double getPrice(List<Operation> operations) {
        return operations.stream()
                .map(Operation::getPrice)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private Double getPriceWithDiscount(Double bookingTotalPrice, Double value) {
        return Math.ceil(bookingTotalPrice * (1 - (value / 100)));
    }

    private int getLeadTime(List<Operation> operations) {
        return operations.stream()
                .map(Operation::getLeadTime)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private LocalTime getEndTime(LocalTime startTime, int leadTime, double ratio) {
        return startTime
                .plusMinutes((int) Math.ceil((leadTime * ratio)));
    }

    private Double getTotalSum(List<Booking> bookings) {
        return bookings.stream()
                .map(Booking::getTotalPrice)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private List<Operation> getUniqueOperations(List<Booking> bookings) {
        return bookings.stream()
                .map(Booking::getOperations)
                .flatMap(List::stream)
                .distinct()
                .toList();
    }

    private boolean checkByDate(LocalDate date, LocalTime startTime) {
        LocalDate todayDate = LocalDate.now();
        LocalTime firstIntervalTime = LocalTime.now();
        LocalTime secondIntervalTime = LocalTime.now().plusMinutes(30);
        return date.isEqual(todayDate) &&
                startTime.isAfter(firstIntervalTime) &&
                startTime.isBefore(secondIntervalTime);
    }
}
