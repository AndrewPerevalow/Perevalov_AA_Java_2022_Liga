package com.ligainternship.carwash.dto.request.booking;

import com.ligainternship.carwash.dto.validate.date.ValidActualDate;
import com.ligainternship.carwash.dto.validate.time.ValidTime;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
public class CreateBookingDto {

    @NotEmpty(message = "Booking date should not be empty")
    @ValidActualDate
    private String date;

    @NotEmpty(message = "Booking time should not be empty")
    @ValidTime
    private String startTime;

    @NotEmpty(message = "List operations should contain as least one operation")
    private List<Long> operations;

    @NotNull(message = "User id should not be empty")
    @Positive(message = "User id should not be negative")
    private Long userId;

}
