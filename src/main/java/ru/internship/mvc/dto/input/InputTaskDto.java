package ru.internship.mvc.dto.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class InputTaskDto {

    @NotEmpty(message = "Header should not be empty")
    private String header;

    @NotEmpty(message = "Description should not be empty")
    private String description;

    @NotNull(message = "Date should not be empty")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    @NotEmpty(message = "Status should not be empty")
    private String status;
}
