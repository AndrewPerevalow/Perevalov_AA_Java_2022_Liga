package com.ligainternship.carwash.rest;

import com.ligainternship.carwash.dto.request.operation.CreateOperationDto;
import com.ligainternship.carwash.dto.response.operation.OperationDto;
import com.ligainternship.carwash.exception.InvalidInputException;
import com.ligainternship.carwash.mapper.operation.CreateOperationMapper;
import com.ligainternship.carwash.model.entitiy.Operation;
import com.ligainternship.carwash.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class OperationController {

    private final OperationService operationService;
    private final CreateOperationMapper createOperationMapper;

    @GetMapping("/operations")
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN','OPERATOR','USER')")
    public Page<OperationDto> findAll(Pageable pageable) {
        List<Operation> operations = operationService.findAll();
        List<OperationDto> listOperationsDto = operations.stream()
                .map(createOperationMapper::entityToDto)
                .toList();
        return new PageImpl<>(listOperationsDto, pageable, 1);
    }

    @PostMapping("/operations")
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public OperationDto create(@Valid @RequestBody CreateOperationDto createOperationDto,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new InvalidInputException(errors);
        }
        Operation operation = operationService.create(createOperationDto);
        return createOperationMapper.entityToDto(operation);
    }
}
