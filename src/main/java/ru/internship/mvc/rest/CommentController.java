package ru.internship.mvc.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.internship.mvc.dto.input.InputCommentDto;
import ru.internship.mvc.service.CommentInfoService;
import ru.internship.mvc.service.CommentService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tracker/v2/comments")
public class CommentController {

    private final CommentService commentService;
    private final CommentInfoService commentInfoService;

    @GetMapping("/manage-comments/{id}")
    public ResponseEntity<?> getOneComment(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(commentInfoService.getOne(id));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.ok(exception.getMessage());
        }
    }

    @GetMapping("/manage-comments")
    public ResponseEntity<?> getAllComments() {
        return ResponseEntity.ok(commentInfoService.getAll());
    }

    @PostMapping("tasks/{id-task}/manage-comments")
    public ResponseEntity<?> createNewComment(@PathVariable("id-task") Long idTask,
                                              @Valid @RequestBody InputCommentDto comment,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.ok(errors);
        }
        try {
            return ResponseEntity.ok(commentService.createComment(idTask, comment));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.ok(exception.getMessage());
        }
    }

    @PutMapping("/manage-comments/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("id") Long id,
                                           @Valid @RequestBody InputCommentDto comment,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.ok(errors);
        }
        try {
            return ResponseEntity.ok(commentService.updateComment(id, comment));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.ok(exception.getMessage());
        }
    }

    @DeleteMapping("/manage-comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(commentService.deleteComment(id));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.ok(exception.getMessage());
        }
    }

}
