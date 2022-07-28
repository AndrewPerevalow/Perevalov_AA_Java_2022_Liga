package ru.internship.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.internship.mvc.model.Comment;
import ru.internship.mvc.repo.CommentRepo;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentInfoService {

    private final CommentRepo commentRepo;

    public Comment getOne(Long id) {
        return commentRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with this id doesn't exist"));
    }

    public List<Comment> getAll() {
        return commentRepo.findAll();
    }
}
