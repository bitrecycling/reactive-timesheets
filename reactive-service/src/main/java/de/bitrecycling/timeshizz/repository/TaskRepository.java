package de.bitrecycling.timeshizz.repository;

import de.bitrecycling.timeshizz.model.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * The task repository provides persistence the the task model
 *
 * created by robo
 */
public interface TaskRepository extends ReactiveMongoRepository<Task, String> {
}
