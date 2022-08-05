package com.ligainternship.carwash.service;

import com.ligainternship.carwash.dto.request.box.CreateBoxDto;
import com.ligainternship.carwash.dto.response.box.BoxDto;
import com.ligainternship.carwash.mapper.box.CreateBoxMapper;
import com.ligainternship.carwash.model.entitiy.Box;
import com.ligainternship.carwash.model.enums.Role;
import com.ligainternship.carwash.repo.BoxRepo;
import com.ligainternship.carwash.service.filter.FilterUserByRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BoxService {

    private final BoxRepo boxRepo;
    private final UserService userService;
    private final CreateBoxMapper createBoxMapper;
    private final FilterUserByRole filterUserByRole;

    public BoxDto create(CreateBoxDto createBoxDto) {
        filterUserByRole.findByIdAndRole(createBoxDto.getUserId(), Role.OPERATOR.getRole());
        Box box = createBoxMapper.dtoToEntity(createBoxDto);
        boxRepo.save(box);
        return createBoxMapper.entityToDto(box);
    }
}
