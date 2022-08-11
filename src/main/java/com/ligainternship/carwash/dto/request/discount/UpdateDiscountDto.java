package com.ligainternship.carwash.dto.request.discount;

import com.ligainternship.carwash.dto.validate.discount.ValidDiscountName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDiscountDto {

    @NotEmpty(message = "Discount name should not be empty")
    @ValidDiscountName
    private String name;

    @NotNull(message = "Discount value should not be null")
    @DecimalMax(value = "100.0", message = "Value should be less 100.0")
    private Double value;
}
