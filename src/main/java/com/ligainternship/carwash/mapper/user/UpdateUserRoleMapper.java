package com.ligainternship.carwash.mapper.user;

import com.ligainternship.carwash.dto.response.user.UserRoleDto;
import com.ligainternship.carwash.model.entitiy.Role;
import com.ligainternship.carwash.model.entitiy.User;
import com.ligainternship.carwash.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserService.class})
public interface UpdateUserRoleMapper {

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "roles", source = "user")
    UserRoleDto entityToDto(User user);

    default List<String> map(User user){
        return user.getRoles().stream().map(Role::getName).toList();
    }
}
