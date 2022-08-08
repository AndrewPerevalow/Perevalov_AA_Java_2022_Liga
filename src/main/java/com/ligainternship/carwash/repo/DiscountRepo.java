package com.ligainternship.carwash.repo;

import com.ligainternship.carwash.model.entitiy.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscountRepo extends JpaRepository<Discount, Long> {
    Optional<Discount> findByName(String name);
}
