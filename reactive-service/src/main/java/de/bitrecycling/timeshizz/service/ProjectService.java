package de.bitrecycling.timeshizz.service;

import de.bitrecycling.timeshizz.model.Project;
import de.bitrecycling.timeshizz.model.Task;
import de.bitrecycling.timeshizz.repository.ClientRepository;
import de.bitrecycling.timeshizz.repository.ProjectRespository;
import de.bitrecycling.timeshizz.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
        return clientRespository.findByName(clientName).flatMap(client -> projectRespository.findAllByClientId(client.getId()));
    }

    public Mono<Project> byId(String id){
        return projectRespository.findById(id);
    }

    public Mono<Project> create(Project project){
        return projectRespository.insert(project);
    }

    public Mono<Task> addTask(String projectId, String taskName){
        Task task = Task.builder().projectId(projectId).name(taskName).build();
        return taskRepository.insert(task);
    }

    public Mono<Project> update(String projectId, String projectName, String projectDescription, Integer rate) {
        return projectRespository.findById(projectId).flatMap(
                p-> {
                    p.setName(projectName);
                    p.setDescription(projectDescription);
                    p.setRate(rate);
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
}
