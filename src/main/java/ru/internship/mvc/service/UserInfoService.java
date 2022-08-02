package ru.internship.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.internship.mvc.dto.output.UserFindMaxTasksDto;
import ru.internship.mvc.model.User;
import ru.internship.mvc.model.enums.Status;
import ru.internship.mvc.repo.UserRepo;
import ru.internship.mvc.service.specifications.FilterByStatusAndDate;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Tuple;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserInfoService {

    private final UserRepo userRepo;
    private final FilterByStatusAndDate filterByStatusAndDate;

    public User getOne(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with this id doesn't exist"));
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public UserFindMaxTasksDto findByMaxTasksCount(String status, Date first, Date second) {
        if (validStatus(status)) {
            List<Tuple> result = filterByStatusAndDate.findByMaxTasksCount(status, first, second);
            return UserFindMaxTasksDto.mapToUserDto(result);
        }
        else {
            throw new InputMismatchException("Incorrect status");
        }
    }

    private boolean validStatus(String status) {
        return status.equals(Status.DEFAULT_STATUS.getStatus()) ||
                status.equals(Status.WORK_STATUS.getStatus()) ||
                status.equals(Status.COMPLETE_STATUS.getStatus());
    }
}
