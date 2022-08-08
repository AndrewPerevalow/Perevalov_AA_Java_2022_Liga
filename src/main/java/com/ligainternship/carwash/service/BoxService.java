package com.ligainternship.carwash.service;

import com.ligainternship.carwash.dto.request.box.CreateBoxDto;
import com.ligainternship.carwash.dto.response.box.BoxDto;
import com.ligainternship.carwash.exception.BookingNotFoundException;
import com.ligainternship.carwash.exception.BoxNotFoundException;
import com.ligainternship.carwash.mapper.box.CreateBoxMapper;
import com.ligainternship.carwash.model.entitiy.Booking;
import com.ligainternship.carwash.model.entitiy.Box;
import com.ligainternship.carwash.model.enums.Role;
import com.ligainternship.carwash.repo.BoxRepo;
import com.ligainternship.carwash.service.filter.FilterUserByRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BoxService {

    private final BoxRepo boxRepo;
    private final CreateBoxMapper createBoxMapper;
    private final FilterUserByRole filterUserByRole;

    @Transactional(readOnly = true)
    public Box findByDateAndOperations(LocalDate date, LocalTime startTime, int totalLeadTimeInSeconds) {
        List<Box> boxList = boxRepo.findByDateAndOperations(date, startTime.getHour(), startTime.getMinute(), totalLeadTimeInSeconds);
        if (boxList.isEmpty()) {
            String message = "Not found free boxes on date: " + date + " and time: "+ startTime;
            log.error(message);
            throw new BoxNotFoundException(message);
        }
        return boxList.get(0);
    }

    @Transactional(readOnly = true)
    public Box findById(Long id) {
        Optional<Box> optionalBox = boxRepo.findById(id);
        if (optionalBox.isEmpty()) {
            String message = "Box with this id not found";
            log.error(message);
            throw new BoxNotFoundException(message);
        }
        return optionalBox.get();
    }

    public BoxDto create(CreateBoxDto createBoxDto) {
        filterUserByRole.findByIdAndRole(createBoxDto.getUserId(), Role.OPERATOR.getRole());
        Box box = createBoxMapper.dtoToEntity(createBoxDto);
        boxRepo.save(box);
        return createBoxMapper.entityToDto(box);
    }

    public Long getBoxId(Box box) {
        return box.getId();
    }
}
