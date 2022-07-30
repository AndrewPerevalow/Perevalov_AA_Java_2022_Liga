package ru.internship.mvc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.internship.mvc.model.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
}
