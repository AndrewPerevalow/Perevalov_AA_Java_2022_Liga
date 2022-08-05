package com.ligainternship.carwash.service;

import com.ligainternship.carwash.exception.UserNotFoundException;
import com.ligainternship.carwash.model.entitiy.User;
import com.ligainternship.carwash.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepo userRepo;

    public User findById(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            String message = "User with this id not found";
            log.error(message);
            throw new UserNotFoundException(message);
        }
        return optionalUser.get();
    }
}
