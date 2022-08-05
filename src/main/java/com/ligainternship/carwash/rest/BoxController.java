package com.ligainternship.carwash.rest;

import com.ligainternship.carwash.dto.request.box.CreateBoxDto;
import com.ligainternship.carwash.exception.InvalidInputException;
import com.ligainternship.carwash.service.BoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class BoxController {

    private final BoxService boxService;

    @PostMapping("/boxes")
    public ResponseEntity<?> createBox(@Valid @RequestBody CreateBoxDto createBoxDto,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new InvalidInputException(errors);
        }
        return ResponseEntity.ok(boxService.create(createBoxDto));
    }

}
