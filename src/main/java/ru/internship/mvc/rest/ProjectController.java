package ru.internship.mvc.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.internship.mvc.model.Project;
import ru.internship.mvc.service.ProjectInfoService;
import ru.internship.mvc.service.ProjectService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tracker/v2/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectInfoService projectInfoService;

    @GetMapping("/manage-projects/{id}")
    public ResponseEntity<?> getOneProject(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(projectInfoService.getOne(id));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.ok(exception.getMessage());
        }
    }

    @GetMapping("/manage-projects")
    public ResponseEntity<?> getAllProjects() {
        return ResponseEntity.ok(projectInfoService.getAll());
    }

    @PostMapping("/manage-projects")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.ok(errors);
        }
        return ResponseEntity.ok(projectService.createProject(project));
    }

    @PutMapping("/manage-projects/{id}")
    public ResponseEntity<?> updateProject(@PathVariable("id") Long id,
                                           @Valid @RequestBody Project project,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.ok(errors);
        }
        try {
            return ResponseEntity.ok(projectService.updateProject(id, project));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.ok(exception.getMessage());
        }
    }

    @DeleteMapping("/manage-projects/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(projectService.deleteProject(id));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.ok(exception.getMessage());
        }
    }
}
