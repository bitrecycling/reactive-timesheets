package de.bitrecycling.timeshizz.management.service;

import de.bitrecycling.timeshizz.common.ResourceNotFoundException;
import de.bitrecycling.timeshizz.management.model.Client;
import de.bitrecycling.timeshizz.management.model.Project;
import de.bitrecycling.timeshizz.management.repository.ClientRepository;
import de.bitrecycling.timeshizz.management.repository.ProjectRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * TODO: guarding to allow normal (logged-in) users only to deal with their owned items
 * The project service
 *
 * created by robo
 */
@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRespository projectRespository;
    private final ClientRepository clientRepository;

    public List<Project> all() {
        return projectRespository.findAll();
    }

    public Project byId(UUID id) {
        return projectRespository.findById(id).orElseThrow(() -> new ResourceNotFoundException("project", id));
    }

    public Project createProjectForClient(Project project, UUID clientId) {
        final Client client = clientRepository.findById(clientId).orElseThrow(() -> new ResourceNotFoundException(String.format("client", clientId)));
        project.setClient(client);
        return projectRespository.save(project);
    }

    public Project update(UUID projectId, String projectName, String projectDescription) {

        final Project projectEntity = byId(projectId);
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

    public List<Project> allByClientId(UUID clientId) {
        return projectRespository.findAllByClientId(clientId);
    }

    public int countAllByClientId(UUID clientId) {
        return projectRespository.findAllByClientId(clientId).size();
    }

    public Project save(Project project) {
        return projectRespository.save(project);
    }
}
