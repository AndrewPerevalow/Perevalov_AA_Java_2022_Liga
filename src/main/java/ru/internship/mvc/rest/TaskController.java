package ru.internship.mvc.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.internship.mvc.dto.input.InputTaskDto;
import ru.internship.mvc.service.TaskInfoService;
import ru.internship.mvc.service.TaskService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.InputMismatchException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tracker/v2/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskInfoService taskInfoService;

    @GetMapping("/manage-tasks/{id}")
    public ResponseEntity<?> getOneTask(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(taskInfoService.getOne(id));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.ok(exception.getMessage());
        }
    }

    @GetMapping("/manage-tasks")
    public ResponseEntity<?> getAllTasks() {
        return ResponseEntity.ok(taskInfoService.getAll());
    }

    @GetMapping("users/{id-user}/manage-tasks/find-tasks")
    public ResponseEntity<?> findTasksForUser(@PathVariable("id-user") Long id) {
        try {
            return ResponseEntity.ok(taskInfoService.printAllTasksForUsers(id));
        } catch (EntityNotFoundException | InputMismatchException exception) {
            return ResponseEntity.ok(exception.getMessage());
        }
    }

    @GetMapping("users/{id-user}/manage-tasks/find-tasks-filter")
    public ResponseEntity<?> findTasksForUserWithFilter(@PathVariable("id-user") Long id,
                                                        @RequestParam("status") String status) {
        try {
            return ResponseEntity.ok(taskInfoService.filterAllTasksForUsersByStatus(id, status));
        } catch (EntityNotFoundException | InputMismatchException exception) {
            return ResponseEntity.ok(exception.getMessage());
        }
    }

    @PostMapping("users/{id-user}/projects/{id-project}/manage-tasks")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewTask(@PathVariable("id-user") Long idUser,
                                           @PathVariable("id-project") Long idProject,
                                           @Valid @RequestBody InputTaskDto taskDto,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.ok(errors);
        }
        try {
            return ResponseEntity.ok(taskService.addNewTask(idUser, idProject, taskDto));
        } catch (EntityNotFoundException | InputMismatchException exception) {
            return ResponseEntity.ok(exception.getMessage());
        }
    }

    @PutMapping("users/{id-user}/projects/{id-project}/manage-tasks/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userInfoService.getOne(#idUser).name eq authentication.name")
    public ResponseEntity<?> updateTask(@PathVariable("id") Long id,
                                        @PathVariable("id-user") Long idUser,
                                        @PathVariable("id-project") Long idProject,
                                        @Valid @RequestBody InputTaskDto taskDto,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.ok(errors);
        }
        try {
            return ResponseEntity.ok(taskService.editTask(id, idUser,idProject, taskDto));
        } catch (EntityNotFoundException | InputMismatchException exception) {
            return ResponseEntity.ok(exception.getMessage());
        }
    }

    @PutMapping("manage-tasks/update-status/{id}")
    @PreAuthorize("hasRole('ADMIN') or #taskInfoService.getOne(id).user.name eq authentication.name")
    public ResponseEntity<?> updateTaskByStatus(@PathVariable("id") Long id,
                                                @RequestParam("status") String status) {
        try {
            return ResponseEntity.ok(taskService.changeTaskStatus(id, status));
        } catch (EntityNotFoundException | InputMismatchException exception) {
            return ResponseEntity.ok(exception.getMessage());
        }
    }

    @DeleteMapping("/manage-tasks/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTask(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(taskService.removeTask(id));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.ok(exception.getMessage());
        }
    }
}
