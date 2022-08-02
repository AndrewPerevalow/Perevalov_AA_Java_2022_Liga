package ru.internship.mvc.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class InputProjectDto {
    @NotEmpty(message = "Header should not be empty")
    private String header;

    @NotEmpty(message = "Description should not be empty")
    private String description;
}
