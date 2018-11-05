package de.bitrecycling.timeshizz.task.repository;

import de.bitrecycling.timeshizz.task.model.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

/**
 * The task repository provides persistence the the task model
 *
 * created by robo
 */
public interface TaskRepository extends ReactiveMongoRepository<Task, String> {

    Flux<Task> findAllByProjectId(String projectId);

    Flux<Task> findByCreationTimeBetween(LocalDateTime from, LocalDateTime to);
}
