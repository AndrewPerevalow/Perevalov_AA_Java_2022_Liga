package com.ligainternship.carwash.dto.response.box;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class BoxDto {
    private Long id;
    private String name;
    private Double ratio;
    private LocalTime workFromTime;
    private LocalTime workToTime;
    private String operatorName;
}
