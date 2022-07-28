package ru.internship.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.internship.mvc.model.Project;
import ru.internship.mvc.repo.ProjectRepo;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepo projectRepo;

    public Project createProject(Project project) {
        return projectRepo.save(project);
    }

    public Project updateProject(Long id, Project updatedProject) {
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
