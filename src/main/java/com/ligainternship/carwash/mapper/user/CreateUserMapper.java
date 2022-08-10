package com.ligainternship.carwash.mapper.user;

import com.ligainternship.carwash.dto.request.user.CreateUserDto;
import com.ligainternship.carwash.dto.response.user.UserDto;
import com.ligainternship.carwash.model.entitiy.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreateUserMapper {

    @Mapping(target = "name", source = "createUserDto.name")
    @Mapping(target = "surname", source = "createUserDto.surname")
    @Mapping(target = "login", source = "createUserDto.login")
    @Mapping(target = "email", source = "createUserDto.email")
    @Mapping(target = "phoneNumber", source = "createUserDto.phoneNumber")
    User dtoToEntity(CreateUserDto createUserDto);

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "surname", source = "user.surname")
    @Mapping(target = "login", source = "user.login")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "phoneNumber", source = "user.phoneNumber")
    UserDto entityToDto(User user);

}
