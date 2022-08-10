package com.ligainternship.carwash.repo;

import com.ligainternship.carwash.model.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
