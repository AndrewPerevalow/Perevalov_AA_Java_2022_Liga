package com.ligainternship.carwash.rest;

import com.ligainternship.carwash.dto.request.user.UpdateUserRoleDto;
import com.ligainternship.carwash.dto.response.user.UserRoleDto;
import com.ligainternship.carwash.exception.InvalidInputException;
import com.ligainternship.carwash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class UserController {

    private final UserService userService;

    @PutMapping("/users/update-role")
    public UserRoleDto updateRole(@Valid @RequestBody UpdateUserRoleDto updateUserRoleDto,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new InvalidInputException(errors);
        }
        return userService.updateRole(updateUserRoleDto);
    }
}
