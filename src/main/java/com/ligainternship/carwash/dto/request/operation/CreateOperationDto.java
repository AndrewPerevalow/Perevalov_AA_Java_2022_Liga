package com.ligainternship.carwash.dto.request.operation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOperationDto {

    @NotEmpty(message = "Booking date should not be empty")
    @Pattern(regexp = "[A-Za-zА-Яа-я\\s]{3,29}", message = "Name should not include digits and symbols, name length should be between 3 and 29")
    private String name;

    @NotNull(message = "Lead time should not be null")
    @Min(value = 15, message = "Min lead time is 15")
    @Max(value = 60, message = "Max lead time is 60")
    private Integer leadTime;

    @NotNull(message = "Price should not be null")
    @DecimalMin(value = "150.0", message = "Price should be more 150.0")
    @DecimalMax(value = "3000.0", message = "Price should be less 3000.0")
    private Double price;
}
