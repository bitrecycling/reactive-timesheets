package de.bitrecycling.timeshizz.task.service;

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

    public Flux<Task> allByProjectId(String projectId) {
        return taskRepository.findAllByProjectId(projectId);
    }

    public Mono<Task> insert(Task task) {
        if(task.getCreationTime() == null){
            task.setCreationTime(LocalDateTime.now());
        }
        return taskRepository.insert(task);
    }

    public Mono<Void> delete(String taskId) {
        return taskRepository.deleteById(taskId);
    }

    public Flux<Task> findByCreationTimeBetween(LocalDateTime from, LocalDateTime to) {
        return taskRepository.findByCreationTimeBetween(from, to);
    }

    public Mono<Task> save(Task task) {
        return taskRepository.save(task);
    }
}
