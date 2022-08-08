package com.ligainternship.carwash.dto.response.operation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperationDto {
    private Long id;
    private String name;
    private Integer leadTime;
    private Double price;
}
