package de.bitrecycling.timeshizz.task.repository;

import de.bitrecycling.timeshizz.task.model.TaskEntry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

/**
 * The taskEntry repository provides persistence the the taskEntry model
 *
 * created by robo
 */
public interface TaskEntryRepository extends ReactiveMongoRepository<TaskEntry, String> {

    Flux<TaskEntry> findAllByTaskIdOrderByCreationTimeDesc(String taskId);
    Flux<TaskEntry> findAllByCreationTimeBetween(LocalDateTime from, LocalDateTime to);
    Flux<TaskEntry> findAllByStartTimeBetween(LocalDateTime from, LocalDateTime to);
    Flux<TaskEntry> findAllByTaskIdAndStartTimeBetween(String clienId, LocalDateTime from, LocalDateTime to);
    Flux<TaskEntry> findAllByOrderByCreationTimeDesc(Pageable pageable);
    Flux<TaskEntry> findAllByOrderByStartTimeDesc(Pageable pageable);
    Flux<TaskEntry> findAllByOrderByCreationTimeAsc(Pageable pageable);
}
