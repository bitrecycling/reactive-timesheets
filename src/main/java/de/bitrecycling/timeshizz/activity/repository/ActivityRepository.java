package de.bitrecycling.timeshizz.activity.repository;

import de.bitrecycling.timeshizz.activity.model.ActivityEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The activityrepository provides persistence the the activitymodel
 *
 * created by robo
 */
public interface ActivityRepository extends CrudRepository<ActivityEntity, UUID> {

    List<ActivityEntity> findAllByProjectIdOrderByCreationTimeDesc(UUID projectId);
    List<ActivityEntity> findByCreationTimeBetween(LocalDateTime from, LocalDateTime to);
}
