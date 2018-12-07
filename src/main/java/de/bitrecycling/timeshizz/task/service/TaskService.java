package de.bitrecycling.timeshizz.task.service;

import de.bitrecycling.timeshizz.common.ResourceNotFoundException;
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

    public Mono<Task> insert(Task task) {
        if(task.getCreationTime() == null){
            task.setCreationTime(LocalDateTime.now());
        }
        return taskRepository.insert(task);
    }

    public Mono<Void> delete(String taskId) {
        return byId(taskId).then(taskRepository.deleteById(taskId));
    }

    public Flux<Task> findByCreationTimeBetween(LocalDateTime from, LocalDateTime to) {
        return taskRepository.findByCreationTimeBetween(from, to);
    }

    public Mono<Task> save(Task task) {
        return byId(task.getId()).then(taskRepository.save(task));
    }
}
