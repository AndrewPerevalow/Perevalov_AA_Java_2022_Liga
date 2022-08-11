package com.ligainternship.carwash.rest;

import com.ligainternship.carwash.dto.request.user.CreateUserDto;
import com.ligainternship.carwash.dto.request.user.UpdateUserRoleDto;
import com.ligainternship.carwash.dto.response.user.UserDto;
import com.ligainternship.carwash.dto.response.user.UserRoleDto;
import com.ligainternship.carwash.exception.InvalidInputException;
import com.ligainternship.carwash.mapper.user.CreateUserMapper;
import com.ligainternship.carwash.mapper.user.UpdateUserRoleMapper;
import com.ligainternship.carwash.model.entitiy.User;
import com.ligainternship.carwash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class UserController {

    private final UserService userService;
    private final UpdateUserRoleMapper updateUserRoleMapper;
    private final CreateUserMapper createUserMapper;

    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.OK)
    public UserDto signUp(@Valid @RequestBody CreateUserDto createUserDto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new InvalidInputException(errors);
        }
        User user = userService.create(createUserDto);
        return createUserMapper.entityToDto(user);
    }

    @PutMapping("/users/update-role")
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public UserRoleDto updateRole(@Valid @RequestBody UpdateUserRoleDto updateUserRoleDto,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new InvalidInputException(errors);
        }
        User user = userService.updateRole(updateUserRoleDto);
        return updateUserRoleMapper.entityToDto(user);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN') and @userService.findById(#id).login ne authentication.name or " +
            "hasRole('USER') and @userService.findById(#id).login eq authentication.name")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
