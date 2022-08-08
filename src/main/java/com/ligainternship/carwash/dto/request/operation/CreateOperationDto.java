package com.ligainternship.carwash.dto.request.operation;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CreateOperationDto {

    @NotEmpty(message = "Booking date should not be empty")
    private String name;
    private Integer leadTime;
    private Double price;
}
