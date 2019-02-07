package de.bitrecycling.timeshizz.task.service;

import de.bitrecycling.timeshizz.client.repository.ClientRepository;
import de.bitrecycling.timeshizz.common.ResourceNotFoundException;
import de.bitrecycling.timeshizz.project.repository.ProjectRespository;
import de.bitrecycling.timeshizz.task.model.Task;
import de.bitrecycling.timeshizz.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * The task service
 * created by robo
 */
@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjectRespository projectRespository;

    @Autowired
    ClientRepository clientRepository;

    public Flux<Task> all() {
        return taskRepository.findAll();
    }

    public Mono<Task> byId(String taskId){
        return taskRepository.findById(taskId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("task", taskId)));
    }

    public Flux<Task> allByProjectId(String projectId) {
        return taskRepository.findAllByProjectIdOrderByCreationTimeDesc(projectId);
    }

    public Mono<Task> insert(String taskName, String projectId) {
        Task task = new Task();
        task.setName(taskName);
        task.setProjectId(projectId);
        Mono<Task> taskMono = projectRespository.findById(task.getProjectId())
                .flatMap(project -> clientRepository.findById(project.getClientId())
                        .flatMap(client -> {
                            task.setClientId(client.getId());
                            task.setCreationTime(LocalDateTime.now());
                            return taskRepository.insert(task);
                        }));


        return taskMono;
    }

    public Mono<Void> delete(String taskId) {
        return byId(taskId).then(taskRepository.deleteById(taskId));
    }

    public Flux<Task> findByCreationTimeBetween(LocalDateTime from, LocalDateTime to) {
        return taskRepository.findByCreationTimeBetween(from, to);
    }

    /**
     *
     * @param task
     * @return
     */
    public Mono<Task> save(Task task) {
        return byId(task.getId()).then(taskRepository.save(task));
    }

    /**
     * saves only possible change: taskName
     * project and client cannot be changed
     * @param id
     * @param taskName
     * @return
     */
    public Mono<Task> save(String id, String taskName) {
        return byId(id).flatMap(task -> taskRepository.save(task));
    }
}
