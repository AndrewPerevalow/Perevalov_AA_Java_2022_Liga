package ru.internship.mvc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.internship.mvc.model.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long> {
}
