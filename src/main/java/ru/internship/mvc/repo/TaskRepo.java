package ru.internship.mvc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.internship.mvc.model.Task;

@Repository
public interface  TaskRepo extends JpaRepository<Task, Long> {
}
