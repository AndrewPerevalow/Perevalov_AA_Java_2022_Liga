package com.ligainternship.carwash.dto.response.booking;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class BookingDto {
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;
    private Double discount;
    private Double totalPrice;
    private List<Long> operations;
    private Long boxId;
}
