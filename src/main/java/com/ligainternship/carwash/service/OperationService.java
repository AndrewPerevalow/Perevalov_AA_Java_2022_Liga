package com.ligainternship.carwash.service;

import com.ligainternship.carwash.dto.request.operation.CreateOperationDto;
import com.ligainternship.carwash.exception.OperationNotFoundException;
import com.ligainternship.carwash.mapper.operation.CreateOperationMapper;
import com.ligainternship.carwash.model.entitiy.Operation;
import com.ligainternship.carwash.repo.OperationRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        return operationList;
    }

    @Transactional(readOnly = true)
    public List<Operation> findAll() {
        return operationRepo.findAll();
    }

    public Operation create(CreateOperationDto createOperationDto) {
        Operation operation = createOperationMapper.dtoToEntity(createOperationDto);
        return operationRepo.save(operation);
    }
}
