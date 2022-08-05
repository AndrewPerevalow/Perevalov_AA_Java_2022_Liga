package com.ligainternship.carwash.repo;

import com.ligainternship.carwash.model.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
