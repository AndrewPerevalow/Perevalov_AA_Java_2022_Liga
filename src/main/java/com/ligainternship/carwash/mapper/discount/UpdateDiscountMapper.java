package com.ligainternship.carwash.mapper.discount;

import com.ligainternship.carwash.dto.request.discount.UpdateDiscountDto;
import com.ligainternship.carwash.dto.response.discount.DiscountDto;
import com.ligainternship.carwash.model.entitiy.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UpdateDiscountMapper {

    @Mapping(target = "name", source = "updateDiscountDto.name")
    @Mapping(target = "value", source = "updateDiscountDto.value")
    Discount dtoToEntity(UpdateDiscountDto updateDiscountDto);

    @Mapping(target = "name", source = "discount.name")
    @Mapping(target = "value", source = "discount.value")
    DiscountDto entityToDto(Discount discount);
}
