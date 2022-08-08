package com.ligainternship.carwash.service;

import com.ligainternship.carwash.exception.RoleNotFoundException;
import com.ligainternship.carwash.model.entitiy.Role;
import com.ligainternship.carwash.repo.RoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleService {

    private final RoleRepo roleRepo;

    @Transactional(readOnly = true)
    public Role findById(Long id) {
        Optional<Role> optionalRole = roleRepo.findById(id);
        if (optionalRole.isEmpty()) {
            String message = "Roles with this id not found";
            log.error(message);
            throw new RoleNotFoundException(message);
        }
        return optionalRole.get();
    }
}
