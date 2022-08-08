package com.ligainternship.carwash.service;

import com.ligainternship.carwash.dto.request.discount.CreateDiscountDto;
import com.ligainternship.carwash.dto.response.discount.DiscountDto;
import com.ligainternship.carwash.exception.DiscountNotFoundException;
import com.ligainternship.carwash.model.entitiy.Booking;
import com.ligainternship.carwash.model.entitiy.Discount;
import com.ligainternship.carwash.model.entitiy.Operation;
import com.ligainternship.carwash.repo.BookingRepo;
import com.ligainternship.carwash.repo.DiscountRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DiscountService {

    private final DiscountRepo discountRepo;
    private final BookingRepo bookingRepo;
    private final BookingService bookingService;

    @Transactional(readOnly = true)
    public Discount findByName(String name) {
        Optional<Discount> optionalDiscount = discountRepo.findByName(name);
        if (optionalDiscount.isEmpty()) {
            String message = "Discount with this name not found";
            log.error(message);
            throw new DiscountNotFoundException(message);
        }
        return optionalDiscount.get();
    }

    public DiscountDto create(CreateDiscountDto createDiscountDto) {
        Booking booking = bookingService.findById(createDiscountDto.getBookingId());
        booking.setDiscount(createDiscountDto.getValue());
        Double totalPrice = Math.ceil(booking.getTotalPrice() * (1 - (createDiscountDto.getValue() / 100)));
        booking.setTotalPrice(totalPrice);
        bookingRepo.save(booking);
        DiscountDto discountDto = new DiscountDto();
        discountDto.setValue(createDiscountDto.getValue());
        discountDto.setTotalPrice(totalPrice);
        return discountDto;
    }

    public void delete(Long id) {
        Booking booking = bookingService.findById(id);
        Double totalPrice = booking.getOperations().stream()
                .map(Operation::getPrice)
                .mapToDouble(Double::doubleValue)
                .sum();
        booking.setTotalPrice(totalPrice);
        booking.setDiscount(0d);
        bookingRepo.save(booking);
    }
}
