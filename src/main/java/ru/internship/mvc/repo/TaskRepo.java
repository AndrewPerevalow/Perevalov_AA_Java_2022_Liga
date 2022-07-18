package ru.internship.mvc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.internship.mvc.model.Task;

public interface TaskRepo extends JpaRepository<Task, Long> {
}
