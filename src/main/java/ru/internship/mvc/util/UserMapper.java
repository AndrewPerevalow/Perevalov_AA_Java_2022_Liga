package ru.internship.mvc.util;

import ru.internship.mvc.dto.input.InputUserDto;
import ru.internship.mvc.model.User;

public class UserMapper {

    public static void DtoToEntity(User user, InputUserDto inputUserDto) {
        user.setName(inputUserDto.getName());
        user.setSurname(inputUserDto.getSurname());
        user.setPatronymic(inputUserDto.getPatronymic());
        user.setLogin(inputUserDto.getLogin());
        user.setEmail(inputUserDto.getEmail());
        user.setPassword(inputUserDto.getPassword());
    }
}
