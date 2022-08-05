package com.ligainternship.carwash.repo;

import com.ligainternship.carwash.model.entitiy.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking, Long> {
}
