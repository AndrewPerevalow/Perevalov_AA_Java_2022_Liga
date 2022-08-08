package com.ligainternship.carwash.mapper.booking;

import com.ligainternship.carwash.dto.response.booking.BookingDto;
import com.ligainternship.carwash.model.entitiy.Booking;
import com.ligainternship.carwash.service.BookingService;
import com.ligainternship.carwash.service.BoxService;
import com.ligainternship.carwash.service.OperationService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BookingService.class, BoxService.class, OperationService.class})
public interface UpdateBookingMapper {

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
}
