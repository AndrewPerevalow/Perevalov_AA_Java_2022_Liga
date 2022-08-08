package com.ligainternship.carwash.service.schedule;

import com.ligainternship.carwash.model.entitiy.Booking;
import com.ligainternship.carwash.model.enums.Status;
import com.ligainternship.carwash.repo.BookingRepo;
import com.ligainternship.carwash.service.filter.FilterBookingByDateAndTimeInterval;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final BookingRepo bookingRepo;
    private final FilterBookingByDateAndTimeInterval filter;

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    public void checkBookingForUserAppear() {
        LocalDate todayDate = LocalDate.now();
        LocalTime firstIntervalTime = LocalTime.now();
        LocalTime secondIntervalTime = LocalTime.now().plusMinutes(10);
        Specification<Booking> specification = filter.getSpec(todayDate, firstIntervalTime, secondIntervalTime);
        List<Booking> bookings = bookingRepo.findAll(specification);
        bookings.forEach(booking -> booking.setStatus(Status.CANCEL.getStatus()));
        bookingRepo.saveAll(bookings);
    }
}
