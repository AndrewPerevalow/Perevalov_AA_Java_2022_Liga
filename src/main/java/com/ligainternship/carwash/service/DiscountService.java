package com.ligainternship.carwash.service;

import com.ligainternship.carwash.dto.request.discount.UpdateDiscountDto;
import com.ligainternship.carwash.dto.response.discount.DiscountDto;
import com.ligainternship.carwash.exception.DiscountNotFoundException;
import com.ligainternship.carwash.mapper.discount.UpdateDiscountMapper;
import com.ligainternship.carwash.model.entitiy.Discount;
import com.ligainternship.carwash.repo.DiscountRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DiscountService {

    private final DiscountRepo discountRepo;
    private final UpdateDiscountMapper updateDiscountMapper;

    @Transactional(readOnly = true)
    public Discount findByName(String name) {
        Optional<Discount> optionalDiscount = discountRepo.findByName(name);
        if (optionalDiscount.isEmpty()) {
            String message = "Discount with this name not found";
            log.error(message);
            throw new DiscountNotFoundException(message);
        }
        return optionalDiscount.get();
    }

    public DiscountDto update(UpdateDiscountDto updateDiscountDto) {
        Discount discount = findByName(updateDiscountDto.getName());
        discount.setName(updateDiscountDto.getName());
        discount.setValue(updateDiscountDto.getValue());
        discountRepo.save(discount);
        return updateDiscountMapper.entityToDto(discount);
    }
}
