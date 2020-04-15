package de.bitrecycling.timeshizz.activity.repository;

import de.bitrecycling.timeshizz.activity.model.ActivityEntryEntity;
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
public interface ActivityEntryRepository extends CrudRepository<ActivityEntryEntity, UUID> {

    List<ActivityEntryEntity> findAllByActivityIdOrderByCreationTimeDesc(UUID taskId);
    List<ActivityEntryEntity> findAllByCreationTimeBetween(LocalDateTime from, LocalDateTime to);
    List<ActivityEntryEntity> findAllByStartTimeBetween(LocalDateTime from, LocalDateTime to);
    List<ActivityEntryEntity> findAllByActivityIdAndStartTimeBetween(UUID taskId, LocalDateTime from, LocalDateTime to);
    List<ActivityEntryEntity> findAllByActivityId(UUID taskId);
    List<ActivityEntryEntity> findAll();
    List<ActivityEntryEntity> findAllByOrderByCreationTimeDesc(Pageable pageable);
    List<ActivityEntryEntity> findAllByOrderByStartTimeDesc(Pageable pageable);
    List<ActivityEntryEntity> findAllByOrderByCreationTimeAsc(Pageable pageable);
}
