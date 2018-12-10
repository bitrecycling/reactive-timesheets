package de.bitrecycling.timeshizz.project.service;

import de.bitrecycling.timeshizz.common.ResourceNotFoundException;
import de.bitrecycling.timeshizz.project.model.Project;
import de.bitrecycling.timeshizz.project.repository.ProjectRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * The project service
 *
 * created by robo
 */
@Service
public class ProjectService {
    @Autowired
    ProjectRespository projectRespository;

    public Flux<Project> all(){
        return projectRespository.findAll();
    }

    public Mono<Project> byId(String id){
        return projectRespository.findById(id).switchIfEmpty(Mono.error(new ResourceNotFoundException("project", id)));
    }

    public Mono<Project> create(Project project){
        if(project.getCreationTime() == null){
            project.setCreationTime(LocalDateTime.now());
        }
        return projectRespository.insert(project);
    }

    public Mono<Project> update(String projectId, String projectName, String projectDescription) {
        return byId(projectId).flatMap(
                p-> {
                    p.setName(projectName);
                    p.setDescription(projectDescription);
                    return projectRespository.save(p);
                }
        );
    }

    /**
     * Just deletes the project, no cleanup here, associated tasks and taskentries will stay in persistence
     *
     * @param projectId
     * @return
     */
    public Mono<Void> delete(String projectId) {
        return byId(projectId).then(projectRespository.deleteById(projectId));
    }

    public Flux<Project> allByClientId(String clientId) {
        return projectRespository.findAllByClientId(clientId);
    }

    public Mono<Long> countAllByClientId(String clientId) {
        return projectRespository.findAllByClientId(clientId).count();
    }

    public Mono<Project> save(Project project) {
        return byId(project.getId())
                .then(projectRespository.save(project));
    }
}
