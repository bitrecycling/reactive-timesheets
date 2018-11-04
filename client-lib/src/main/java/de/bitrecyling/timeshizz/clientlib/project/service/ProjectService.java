package de.bitrecyling.timeshizz.clientlib.project.service;

import de.bitrecyling.timeshizz.clientlib.project.connector.ProjectConnector;
import de.bitrecyling.timeshizz.clientlib.project.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Project Service API, provides all services to the project resource. Uses the ProjectConnector internally.
 * Provides some additional logic to ensure correctness and for convenience.
 * <p>
 * created by robo
 */
@Service
public class ProjectService {

    @Autowired
    ProjectConnector projectConnector;


    public Flux<Project> loadAllProjects() {

        return projectConnector.loadAllProjects();
    }

    public Mono<Project> createProject(Project project) {
        if (project.getClientId().isEmpty()) {
            throw new RuntimeException("project has no clientId. projects can only be created for existing client");
        }
        return projectConnector.createProject(project);
    }

    public Mono<Void> deleteProject(String id) {
        return projectConnector.deleteProject(id);
    }
}
