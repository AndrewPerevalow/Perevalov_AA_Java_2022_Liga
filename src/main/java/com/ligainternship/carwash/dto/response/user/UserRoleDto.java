package com.ligainternship.carwash.dto.response.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRoleDto {
    private Long id;
    private String name;
    private List<String> roles;
}
