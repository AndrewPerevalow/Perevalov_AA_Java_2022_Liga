package com.ligainternship.carwash.rest;

import com.ligainternship.carwash.dto.request.operation.CreateOperationDto;
import com.ligainternship.carwash.dto.response.operation.OperationDto;
import com.ligainternship.carwash.exception.InvalidInputException;
import com.ligainternship.carwash.service.OperationService;
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
public class OperationController {

    private final OperationService operationService;

    @PostMapping("/operations")
    @ResponseStatus(code = HttpStatus.OK)
    public OperationDto create(@Valid @RequestBody CreateOperationDto createOperationDto,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new InvalidInputException(errors);
        }
        return operationService.create(createOperationDto);
    }
}
