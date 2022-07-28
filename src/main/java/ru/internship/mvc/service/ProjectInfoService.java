package ru.internship.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.internship.mvc.model.Project;
import ru.internship.mvc.repo.ProjectRepo;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectInfoService {

    private final ProjectRepo projectRepo;

    public Project getOne(Long id) {
        return projectRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project with this id doesn't exist"));
    }

    public List<Project> getAll() {
        return projectRepo.findAll();
    }
}
