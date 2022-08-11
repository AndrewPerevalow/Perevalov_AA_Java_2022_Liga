package com.ligainternship.carwash.dto.request.booking;

import com.ligainternship.carwash.dto.validate.status.ValidCancelStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CancelBookingDto {

    @NotNull(message = "Id should not be empty")
    @Positive(message = "Id should not be negative")
    private Long id;

    @NotEmpty(message = "Status should not be empty")
    @ValidCancelStatus
    private String status;
}
