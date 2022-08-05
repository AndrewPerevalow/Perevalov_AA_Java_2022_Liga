package com.ligainternship.carwash.dto.request.box;

import com.ligainternship.carwash.dto.validate.time.ValidTime;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class CreateBoxDto {

    @NotEmpty(message = "Name should not be empty")
    @Pattern(regexp = "[A-Za-zА-Яа-я]{3,29}", message = "Name should not include digits and symbols, name length should be between 3 and 29")
    private String name;

    @NotNull(message = "Ratio should not be null")
    @DecimalMin(value = "0.1", message = "Ratio should be more 0.1")
    @DecimalMax(value = "1.9", message = "Ratio should be less 1.9")
    private Double ratio;

    @NotEmpty(message = "Start time should not be empty")
    @ValidTime
    private String workFromTime;

    @NotEmpty(message = "End time should not be empty")
    @ValidTime
    private String workToTime;

    @NotNull(message = "User id should not be null")
    @Positive(message = "User id should not be negative")
    private Long userId;
}
