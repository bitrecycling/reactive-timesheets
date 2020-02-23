package de.bitrecycling.timeshizz.project.service;

import de.bitrecycling.timeshizz.common.ResourceNotFoundException;
import de.bitrecycling.timeshizz.project.model.ProjectEntity;
import de.bitrecycling.timeshizz.project.repository.ProjectRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The project service
 *
 * created by robo
 */
@Service
public class ProjectService {
    @Autowired
    ProjectRespository projectRespository;

    public Iterable<ProjectEntity> all() {
        return projectRespository.findAll();
    }

    public ProjectEntity byId(UUID id) {
        return projectRespository.findById(id).orElseThrow(() -> new ResourceNotFoundException("project", id.toString()));
    }

    public ProjectEntity create(ProjectEntity project) {
        return projectRespository.save(project);
    }

    public ProjectEntity update(UUID projectId, String projectName, String projectDescription) {

        final ProjectEntity projectEntity = byId(projectId);
        projectEntity.setName(projectName);
        projectEntity.setDescription(projectDescription);
        return projectRespository.save(projectEntity);
    }

    /**
     * Just deletes the project, no cleanup here, associated tasks and taskentries will stay in persistence
     *
     * @param projectId
     * @return
     */
    public void delete(UUID projectId) {
        projectRespository.deleteById(projectId);
    }

    public Iterable<ProjectEntity> allByClientId(UUID clientId) {
        return projectRespository.findAllByClientId(clientId);
    }

    public int countAllByClientId(UUID clientId) {
        return projectRespository.findAllByClientId(clientId).size();
    }

    public ProjectEntity save(ProjectEntity project) {
        return projectRespository.save(project);
    }
}
