package com.ligainternship.carwash.repo;

import com.ligainternship.carwash.model.entitiy.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
}
