package ru.internship.mvc.rest.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.internship.mvc.dto.security.requests.LoginRequest;
import ru.internship.mvc.dto.security.requests.RefreshRequest;
import ru.internship.mvc.service.security.jwt.AuthenticationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tracker/v2/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/sign-in")
    public ResponseEntity<?> authorizeUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.ok(errors);
        }
        return ResponseEntity.ok(authenticationService.authUser(loginRequest));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshRequest refreshRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.ok(errors);
        }
        return ResponseEntity.ok(authenticationService.refreshToken(refreshRequest));
    }
}
