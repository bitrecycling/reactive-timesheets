package de.bitrecycling.timeshizz.service;

import de.bitrecycling.timeshizz.model.Project;
import de.bitrecycling.timeshizz.model.Task;
import de.bitrecycling.timeshizz.repository.ClientRepository;
import de.bitrecycling.timeshizz.repository.ProjectRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProjectService {
    @Autowired
    ProjectRespository projectRespository;
    @Autowired
    ClientRepository clientRespository;

    public Flux<Project> all(){
        return projectRespository.findAll();
    }

    public Flux<Project> byClient(String clientName){
        return projectRespository.findAllByClient(
                clientRespository.findDistinctFirstByName(clientName));

    }

    public Mono<Project> byId(String id){
        return projectRespository.findById(id);
    }

    public Mono<Project> create(Project project){
        return projectRespository.insert(project);
    }

    public Mono<Project> addTask(Project project, Task task){
        project.getTaskIds().add(task.getId());
        return projectRespository.save(project);
    }
}
