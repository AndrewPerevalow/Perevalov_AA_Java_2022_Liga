package com.ligainternship.carwash.service;

import com.ligainternship.carwash.dto.request.operation.CreateOperationDto;
import com.ligainternship.carwash.dto.response.operation.OperationDto;
import com.ligainternship.carwash.exception.OperationNotFoundException;
import com.ligainternship.carwash.mapper.operation.CreateOperationMapper;
import com.ligainternship.carwash.model.entitiy.Operation;
import com.ligainternship.carwash.repo.OperationRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OperationService {

    private final OperationRepo operationRepo;
    private final CreateOperationMapper createOperationMapper;

    @Transactional(readOnly = true)
    public List<Operation> findAllById(List<Long> ids) {
        List<Operation> operationList = operationRepo.findAllById(ids);
        if (operationList.isEmpty()) {
            String message = "Operations with this ids not found";
            log.error(message);
            throw new OperationNotFoundException(message);
        }
        return operationRepo.findAllById(ids);
    }

    @Transactional(readOnly = true)
    public Page<OperationDto> findAll(Pageable pageable) {
        List<Operation> operations = operationRepo.findAll();
        List<OperationDto> listOperationsDto = operations.stream()
                .map(createOperationMapper::entityToDto)
                .toList();
        return new PageImpl<>(listOperationsDto, pageable, 1);
    }

    public OperationDto create(CreateOperationDto createOperationDto) {
        Operation operation = createOperationMapper.dtoToEntity(createOperationDto);
        operationRepo.save(operation);
        return createOperationMapper.entityToDto(operation);
    }
}
