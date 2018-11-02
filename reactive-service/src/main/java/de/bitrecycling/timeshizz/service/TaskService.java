package de.bitrecycling.timeshizz.service;

import de.bitrecycling.timeshizz.model.Task;
import de.bitrecycling.timeshizz.repository.TaskRepository;
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

    public Mono<Task> insert(Task task) {
        return taskRepository.insert(task);
    }

    public Mono<Void> delete (String taskId){
        return taskRepository.deleteById(taskId);
    }
}
