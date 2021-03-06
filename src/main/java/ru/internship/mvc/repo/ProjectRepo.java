package ru.internship.mvc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.internship.mvc.model.Project;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {
}
