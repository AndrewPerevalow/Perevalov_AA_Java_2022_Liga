package com.ligainternship.carwash.rest;

import com.ligainternship.carwash.dto.request.box.CreateBoxDto;
import com.ligainternship.carwash.dto.response.box.BoxDto;
import com.ligainternship.carwash.exception.InvalidInputException;
import com.ligainternship.carwash.mapper.box.CreateBoxMapper;
import com.ligainternship.carwash.model.entitiy.Box;
import com.ligainternship.carwash.service.BoxService;
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
public class BoxController {

    private final BoxService boxService;
    private final CreateBoxMapper createBoxMapper;

    @PostMapping("/boxes")
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public BoxDto create(@Valid @RequestBody CreateBoxDto createBoxDto,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new InvalidInputException(errors);
        }
        Box box = boxService.create(createBoxDto);
        return createBoxMapper.entityToDto(box);
    }
}
