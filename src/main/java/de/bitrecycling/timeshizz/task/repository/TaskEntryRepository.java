package de.bitrecycling.timeshizz.task.repository;

import de.bitrecycling.timeshizz.task.model.TaskEntryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The taskEntry repository provides persistence the the taskEntry model
 *
 * created by robo
 */
public interface TaskEntryRepository extends CrudRepository<TaskEntryEntity, UUID> {

    List<TaskEntryEntity> findAllByTaskIdOrderByCreationTimeDesc(UUID taskId);
    List<TaskEntryEntity> findAllByCreationTimeBetween(LocalDateTime from, LocalDateTime to);
    List<TaskEntryEntity> findAllByStartTimeBetween(LocalDateTime from, LocalDateTime to);
    List<TaskEntryEntity> findAllByTaskIdAndStartTimeBetween(UUID taskId, LocalDateTime from, LocalDateTime to);
    List<TaskEntryEntity> findAllByTaskId(UUID taskId);
    List<TaskEntryEntity> findAll();
    List<TaskEntryEntity> findAllByOrderByCreationTimeDesc(Pageable pageable);
    List<TaskEntryEntity> findAllByOrderByStartTimeDesc(Pageable pageable);
    List<TaskEntryEntity> findAllByOrderByCreationTimeAsc(Pageable pageable);
}
