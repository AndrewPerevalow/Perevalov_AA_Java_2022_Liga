package ru.internship.mvc.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.internship.mvc.dto.input.InputUserDto;
import ru.internship.mvc.service.UserInfoService;
import ru.internship.mvc.service.UserService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tracker/v2/users")
public class UserController {

    private final UserService userService;
    private final UserInfoService userInfoService;

    @GetMapping("/manage-users/{id}")
    public ResponseEntity<?> getOneUser(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(userInfoService.getOne(id));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.ok(exception.getMessage());
        }
    }

    @GetMapping("/manage-users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userInfoService.getAll());
    }

    @GetMapping("/manage-users/find-max-and-filter")
    public ResponseEntity<?> findUserWithMaxTasks(@RequestParam("status") String status,
          @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("date-1") Date firstDate,
          @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("date-2") Date secondDate) {
        try {
            return ResponseEntity.ok(userInfoService.findByMaxTasksCount(status, firstDate, secondDate));
        } catch (EntityNotFoundException | InputMismatchException exception) {
            return ResponseEntity.ok(exception.getMessage());
        }
    }

    @PostMapping("/projects/{id-project}/manage-users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewUser(@PathVariable("id-project") Long idProject,
                                           @Valid @RequestBody InputUserDto userDto,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.ok(errors);
        }
        try {
            return ResponseEntity.ok(userService.addNewUser(idProject, userDto));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.ok(exception.getMessage());
        }
    }

    @PutMapping("/manage-users/{id}")
    @PreAuthorize("hasRole('ADMIN') or #userDto.name eq authentication.name")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id,
                                        @Valid @RequestBody InputUserDto userDto,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.ok(errors);
        }
        try {
            return ResponseEntity.ok(userService.updateUser(id, userDto));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.ok(exception.getMessage());
        }
    }

    @DeleteMapping("/manage-users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(userService.removeUser(id));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.ok(exception.getMessage());
        }
    }
}
