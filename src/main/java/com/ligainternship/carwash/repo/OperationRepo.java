package com.ligainternship.carwash.repo;

import com.ligainternship.carwash.model.entitiy.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepo extends JpaRepository<Operation, Long> {
}
