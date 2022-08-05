package com.ligainternship.carwash.repo;

import com.ligainternship.carwash.model.entitiy.Service;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ServiceRepo extends JpaRepository<Service, Long> {
}
