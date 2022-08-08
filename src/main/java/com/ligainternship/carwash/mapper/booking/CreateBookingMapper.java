package com.ligainternship.carwash.mapper.booking;

import com.ligainternship.carwash.dto.request.booking.CreateBookingDto;
import com.ligainternship.carwash.dto.response.booking.BookingDto;
import com.ligainternship.carwash.model.entitiy.Booking;
import com.ligainternship.carwash.service.BoxService;
import com.ligainternship.carwash.service.OperationService;
import com.ligainternship.carwash.service.UserService;
import com.ligainternship.carwash.util.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OperationService.class, UserService.class, BoxService.class, StringUtils.class})
public interface CreateBookingMapper {

    @Mapping(target = "id", source = "booking.id")
    @Mapping(target = "date", source = "booking.date")
    @Mapping(target = "startTime", source = "booking.startTime")
    @Mapping(target = "endTime", source = "booking.endTime")
    @Mapping(target = "status", source = "booking.status")
    @Mapping(target = "discount", source = "booking.discount")
    @Mapping(target = "totalPrice", source = "booking.totalPrice")
    @Mapping(target = "operations", source = "booking.operations")
    @Mapping(target = "boxId", source = "booking.box")
    BookingDto entityToDto(Booking booking);

    @Mapping(target = "date", source = "createBookingDto.date")
    @Mapping(target = "startTime", source = "createBookingDto.startTime")
    @Mapping(target = "operations", source = "createBookingDto.operations")
    @Mapping(target = "user", source = "createBookingDto.userId")
    Booking dtoToEntity(CreateBookingDto createBookingDto);


}
