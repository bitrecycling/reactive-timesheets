package de.bitrecycling.timeshizz.task.repository;

import de.bitrecycling.timeshizz.task.model.TaskEntry;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
 * The taskentry repository provides persistence the the taskentry model
 *
 * created by robo
 */
public interface TaskEntryRepository extends ReactiveMongoRepository<TaskEntry, String> {
    Flux<TaskEntry> findAllByTaskId(String taskId);
}
