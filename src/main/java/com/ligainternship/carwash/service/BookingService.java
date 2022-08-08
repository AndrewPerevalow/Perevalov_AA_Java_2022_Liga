package com.ligainternship.carwash.service;

import com.ligainternship.carwash.dto.request.booking.CancelBookingDto;
import com.ligainternship.carwash.dto.request.booking.CreateBookingDto;
import com.ligainternship.carwash.dto.request.discount.CreateDiscountDto;
import com.ligainternship.carwash.dto.response.booking.BookingDto;
import com.ligainternship.carwash.dto.response.booking.TotalSumDto;
import com.ligainternship.carwash.exception.BookingNotFoundException;
import com.ligainternship.carwash.mapper.booking.CreateBookingMapper;
import com.ligainternship.carwash.mapper.booking.UpdateBookingMapper;
import com.ligainternship.carwash.model.entitiy.Booking;
import com.ligainternship.carwash.model.entitiy.Box;
import com.ligainternship.carwash.model.entitiy.Operation;
import com.ligainternship.carwash.model.enums.Status;
import com.ligainternship.carwash.repo.BookingRepo;
import com.ligainternship.carwash.service.filter.FilterBookingByBoxIdAndDate;
import com.ligainternship.carwash.service.filter.FilterBookingByDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    private final CreateBookingMapper createBookingMapper;
    private final UpdateBookingMapper updateBookingMapper;
    private final FilterBookingByBoxIdAndDate filterBookingByBoxIdAndDate;
    private final FilterBookingByDate filterBookingByDate;

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
        Specification<Booking> specification = filterBookingByBoxIdAndDate.getSpec(box, date, time);
        Page<Booking> bookings = bookingRepo.findAll(specification, pageable);
        return bookings.map(createBookingMapper::entityToDto);
    }

    @Transactional(readOnly = true)
    public Page<TotalSumDto> findSumTotalPriceByDate(LocalDate fromDate, LocalDate toDate, Pageable pageable) {
        Specification<Booking> specification = filterBookingByDate.getSpec(fromDate, toDate);
        List<Booking> bookings = bookingRepo.findAll(specification);
        Double totalSum = getTotalSum(bookings);
        TotalSumDto totalSumDto = new TotalSumDto();
        totalSumDto.setTotalSum(totalSum);
        return new PageImpl<>(List.of(totalSumDto), pageable, 1);
    }

    public BookingDto create(CreateBookingDto createBookingDto) {
        Booking booking = createBookingMapper.dtoToEntity(createBookingDto);
        int totalLeadTime = getLeadTime(booking.getOperations());
        Box box = boxService.findByDateAndOperations(booking.getDate(), booking.getStartTime(), totalLeadTime);
        booking.setBox(box);
        Double totalPrice = getPrice(booking.getOperations());
        booking.setTotalPrice(totalPrice);
        booking.setStatus(Status.ACTIVE.getStatus());
        LocalTime endTime = getEndTime(booking.getStartTime(), totalLeadTime, box.getRatio());
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

    public BookingDto createDiscount(CreateDiscountDto createDiscountDto) {
        Booking booking = findById(createDiscountDto.getBookingId());
        booking.setDiscount(createDiscountDto.getValue());
        Double totalPrice = getPriceWithDiscount(getPrice(booking.getOperations()), createDiscountDto.getValue());
        booking.setTotalPrice(totalPrice);
        bookingRepo.save(booking);
        return updateBookingMapper.entityToDto(booking);
    }

    public void deleteDiscount(Long id) {
        Booking booking = findById(id);
        Double totalPrice = getPrice(booking.getOperations());
        booking.setTotalPrice(totalPrice);
        booking.setDiscount(0d);
        bookingRepo.save(booking);
    }

    private Double getPrice(List<Operation> operations) {
        return operations.stream()
                .map(Operation::getPrice)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private  Double getPriceWithDiscount(Double bookingTotalPrice, Double value) {
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
}
