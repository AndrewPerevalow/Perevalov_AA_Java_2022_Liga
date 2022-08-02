package ru.internship.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.internship.mvc.dto.input.InputProjectDto;
import ru.internship.mvc.model.Project;
import ru.internship.mvc.repo.ProjectRepo;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {

    private final ProjectRepo projectRepo;

    public Project createProject(InputProjectDto inputProject) {
        Project project = new Project();
        project.setHeader(inputProject.getHeader());
        project.setDescription(inputProject.getDescription());
        return projectRepo.save(project);
    }

    public Project updateProject(Long id, InputProjectDto updatedProject) {
        Project project = projectRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project with this id doesn't exist"));
        project.setHeader(updatedProject.getHeader());
        project.setDescription(updatedProject.getDescription());
        return projectRepo.save(project);
    }

    public String deleteProject(Long id) {
        projectRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Project with this id doesn't exist"));
        projectRepo.deleteById(id);
        return "Project: " + id + " deleted";
    }
}
