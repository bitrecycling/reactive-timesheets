package de.bitrecycling.timeshizz.task.repository;

import de.bitrecycling.timeshizz.task.model.TaskEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The task repository provides persistence the the task model
 *
 * created by robo
 */
public interface TaskRepository extends CrudRepository<TaskEntity, UUID> {

    List<TaskEntity> findAllByProjectIdOrderByCreationTimeDesc(UUID projectId);

    List<TaskEntity> findByCreationTimeBetween(LocalDateTime from, LocalDateTime to);
}
