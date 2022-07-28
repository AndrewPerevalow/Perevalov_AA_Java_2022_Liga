package ru.internship.mvc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.internship.mvc.model.Project;

public interface ProjectRepo extends JpaRepository<Project, Long> {
}
