package com.ligainternship.carwash.dto.request.discount;

import com.ligainternship.carwash.dto.validate.discount.ValidDiscount;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class CreateDiscountDto {

    @NotNull(message = "Discount should not be null")
    @ValidDiscount
    private Double value;

    @NotNull(message = "Booking id should not be null")
    @Positive(message = "Booking id should not be negative")
    private Long bookingId;
}
