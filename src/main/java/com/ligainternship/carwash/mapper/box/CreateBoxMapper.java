package com.ligainternship.carwash.mapper.box;

import com.ligainternship.carwash.dto.request.box.CreateBoxDto;
import com.ligainternship.carwash.dto.response.box.BoxDto;
import com.ligainternship.carwash.model.entitiy.Box;
import com.ligainternship.carwash.repo.UserRepo;
import com.ligainternship.carwash.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {UserService.class})
public interface CreateBoxMapper {

//    CreateBoxMapper INSTANCE = Mappers.getMapper(CreateBoxMapper.class);

    @Mapping(target = "id", source = "box.id")
    @Mapping(target = "name", source = "box.name")
    @Mapping(target = "ratio", source = "box.ratio")
    @Mapping(target = "workFromTime", source = "box.workFromTime")
    @Mapping(target = "workToTime", source = "box.workToTime")
    @Mapping(target = "operatorName", source = "user.name")
    BoxDto entityToDto(Box box);

    @Mapping(target = "name", source = "createBoxDto.name")
    @Mapping(target = "ratio", source = "createBoxDto.ratio")
    @Mapping(target = "workFromTime", source = "createBoxDto.workFromTime")
    @Mapping(target = "workToTime", source = "createBoxDto.workToTime")
    @Mapping(target = "user", source = "createBoxDto.userId")
    Box dtoToEntity(CreateBoxDto createBoxDto);
}
