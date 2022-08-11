package com.ligainternship.carwash.dto.request.discount;

import com.ligainternship.carwash.dto.validate.discount.ValidDiscount;
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
public class CreateDiscountDto {

    @NotNull(message = "Discount should not be null")
    @ValidDiscount
    private Double value;

    @NotNull(message = "Booking id should not be null")
    @Positive(message = "Booking id should not be negative")
    private Long bookingId;
}
