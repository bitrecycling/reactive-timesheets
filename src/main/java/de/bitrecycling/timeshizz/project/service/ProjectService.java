package de.bitrecycling.timeshizz.project.service;

import de.bitrecycling.timeshizz.client.model.ClientEntity;
import de.bitrecycling.timeshizz.client.repository.ClientRepository;
import de.bitrecycling.timeshizz.common.ResourceNotFoundException;
import de.bitrecycling.timeshizz.project.model.ProjectEntity;
import de.bitrecycling.timeshizz.project.repository.ProjectRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    @Autowired
    ClientRepository clientRepository;

    public List<ProjectEntity> all() {
        return projectRespository.findAll();
    }

    public ProjectEntity byId(UUID id) {
        return projectRespository.findById(id).orElseThrow(() -> new ResourceNotFoundException("project", id.toString()));
    }

    public ProjectEntity createProjectForClient(ProjectEntity project, UUID clientId) {
        final ClientEntity client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException(String.format("client [%] not found", clientId)));
        project.setClient(client);
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

    public List<ProjectEntity> allByClientId(UUID clientId) {
        return projectRespository.findAllByClientId(clientId);
    }

    public int countAllByClientId(UUID clientId) {
        return projectRespository.findAllByClientId(clientId).size();
    }

    public ProjectEntity save(ProjectEntity project) {
        return projectRespository.save(project);
    }
}
