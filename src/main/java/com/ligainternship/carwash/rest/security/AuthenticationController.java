package com.ligainternship.carwash.rest.security;

import com.ligainternship.carwash.dto.request.security.LoginDto;
import com.ligainternship.carwash.dto.request.security.RefreshDto;
import com.ligainternship.carwash.dto.response.security.JwtResponse;
import com.ligainternship.carwash.exception.InvalidInputException;
import com.ligainternship.carwash.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/auth/login")
    @ResponseStatus(code = HttpStatus.OK)
    public JwtResponse login(@Valid @RequestBody LoginDto loginDto,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new InvalidInputException(errors);
        }
        return authenticationService.login(loginDto);
    }

    @PostMapping("/auth/access-token")
    @ResponseStatus(code = HttpStatus.OK)
    public JwtResponse getNewAccessToken(@Valid @RequestBody RefreshDto refreshDto,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new InvalidInputException(errors);
        }
        return authenticationService.getNewAccessToken(refreshDto);
    }
}
