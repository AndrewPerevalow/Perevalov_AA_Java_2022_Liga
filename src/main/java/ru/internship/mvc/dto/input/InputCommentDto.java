package ru.internship.mvc.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class InputCommentDto {

    @NotEmpty(message = "Comment should not be empty")
    private String content;
}
