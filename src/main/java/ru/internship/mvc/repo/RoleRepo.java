package ru.internship.mvc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.internship.mvc.model.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
}
