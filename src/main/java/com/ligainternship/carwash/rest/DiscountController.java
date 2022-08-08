package com.ligainternship.carwash.rest;

import com.ligainternship.carwash.dto.request.discount.CreateDiscountDto;
import com.ligainternship.carwash.dto.response.discount.DiscountDto;
import com.ligainternship.carwash.exception.InvalidInputException;
import com.ligainternship.carwash.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping("/discounts")
    @ResponseStatus(code = HttpStatus.OK)
    public DiscountDto create(@Valid @RequestBody CreateDiscountDto createDiscountDto,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new InvalidInputException(errors);
        }
        return discountService.create(createDiscountDto);
    }

    @DeleteMapping("/discounts/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        discountService.delete(id);
    }
}
