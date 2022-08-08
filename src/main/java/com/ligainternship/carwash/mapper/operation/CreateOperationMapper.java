package com.ligainternship.carwash.mapper.operation;

import com.ligainternship.carwash.dto.request.operation.CreateOperationDto;
import com.ligainternship.carwash.dto.response.operation.OperationDto;
import com.ligainternship.carwash.model.entitiy.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreateOperationMapper {

    @Mapping(target = "name", source = "createOperationDto.name")
    @Mapping(target = "leadTime", source = "createOperationDto.leadTime")
    @Mapping(target = "price", source = "createOperationDto.price")
    Operation dtoToEntity(CreateOperationDto createOperationDto);

    @Mapping(target = "id", source = "operation.id")
    @Mapping(target = "name", source = "operation.name")
    @Mapping(target = "leadTime", source = "operation.leadTime")
    @Mapping(target = "price", source = "operation.price")
    OperationDto entityToDto(Operation operation);
}
