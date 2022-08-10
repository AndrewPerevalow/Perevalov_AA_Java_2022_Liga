package com.ligainternship.carwash.dto.response.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String name;
    private String surname;
    private String login;
    private String email;
    private String phoneNumber;

}
