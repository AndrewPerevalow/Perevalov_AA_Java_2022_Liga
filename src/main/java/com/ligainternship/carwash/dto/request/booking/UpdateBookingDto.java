package com.ligainternship.carwash.dto.request.booking;

import com.ligainternship.carwash.dto.validate.status.ValidStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class UpdateBookingDto {

    private String date;
    private String startTime;

    @NotEmpty(message = "List operations should contain as least one operation")
    private List<Long> operations;

    private boolean cancel;

}
