package ru.internship.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.internship.mvc.model.Comment;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.repo.CommentRepo;
import ru.internship.mvc.repo.TaskRepo;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepo commentRepo;
    private final TaskRepo taskRepo;

    public Comment createComment(Long idTask, Comment comment) {
        Task task = taskRepo.findById(idTask)
                .orElseThrow(() -> new EntityNotFoundException("Task with this id doesn't exist"));
        comment.setTask(task);
        return commentRepo.save(comment);
    }

    public Comment updateComment(Long id, Comment updatedComment) {
        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with this id doesn't exist"));
        comment.setContent(updatedComment.getContent());
        return commentRepo.save(comment);
    }

    public String deleteComment(Long id) {
        commentRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment with this id doesn't exist"));
        commentRepo.deleteById(id);
        return "Comment: " + id + " deleted";
    }

}
