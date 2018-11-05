package de.bitrecycling.timeshizz.task.service;

import de.bitrecycling.timeshizz.task.model.Task;
import de.bitrecycling.timeshizz.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
        return taskRepository.insert(task);
    }

    public Mono<Void> delete(String taskId) {
        return taskRepository.deleteById(taskId);
    }
}
