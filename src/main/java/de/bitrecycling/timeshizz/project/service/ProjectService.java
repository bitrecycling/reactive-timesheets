package de.bitrecycling.timeshizz.project.service;

import de.bitrecycling.timeshizz.client.repository.ClientRepository;
import de.bitrecycling.timeshizz.project.model.Project;
import de.bitrecycling.timeshizz.project.repository.ProjectRespository;
import de.bitrecycling.timeshizz.task.model.Task;
import de.bitrecycling.timeshizz.task.repository.TaskRepository;
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
    @Autowired
    ClientRepository clientRespository;
    @Autowired
    TaskRepository taskRepository;

    public Flux<Project> all(){
        return projectRespository.findAll();
    }

    public Flux<Project> byClientName(String clientName){
        return clientRespository.findByName(clientName)
                .flatMap(
                        client -> projectRespository.findAllByClientId(client.getId())
                );
    }

    public Mono<Project> byId(String id){
        return projectRespository.findById(id);
    }

    public Mono<Project> create(Project project){
        if(project.getCreationTime() == null){
            project.setCreationTime(LocalDateTime.now());
        }
        return projectRespository.insert(project);
    }

    public Mono<Task> addTask(String projectId, String taskName){
        Task task = new Task(taskName, projectId);
        return taskRepository.insert(task);
    }

    public Mono<Project> update(String projectId, String projectName, String projectDescription) {
        return projectRespository.findById(projectId).flatMap(
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
        return projectRespository.deleteById(projectId);
    }

    public Flux<Project> allByClientId(String clientId) {
        return projectRespository.findAllByClientId(clientId);
    }

    public Mono<Long> countAllByClientId(String clientId) {
        return projectRespository.findAllByClientId(clientId).count();
    }

    public Mono<Project> save(Project project) {
        return  projectRespository.save(project);
    }


}
