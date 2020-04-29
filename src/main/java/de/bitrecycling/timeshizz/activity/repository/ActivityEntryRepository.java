package de.bitrecycling.timeshizz.activity.repository;

import de.bitrecycling.timeshizz.activity.model.ActivityEntryEntity;
import de.bitrecycling.timeshizz.client.model.ClientEntity;
import de.bitrecycling.timeshizz.project.model.ProjectEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
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
    
    List<ActivityEntryEntity> findAllByActivityIdAndUserIdOrderByCreationTimeDesc(UUID taskId, UUID userId);
    List<ActivityEntryEntity> findAllByUserIdAndCreationTimeBetween(LocalDateTime from, LocalDateTime to, UUID userId);
    List<ActivityEntryEntity> findAllByUserIdAndStartTimeBetween(LocalDateTime from, LocalDateTime to, UUID userId);
    List<ActivityEntryEntity> findAllByActivityIdAndUserIdAndStartTimeBetween(UUID taskId, LocalDateTime from, LocalDateTime to, UUID userId);
    
    @Query(value="from ActivityEntryEntity ae where ae.activity.id = :activityId and ae.startTime > :from and ae.startTime < :to")
    List<ActivityEntryEntity> findActivityEntriesForActivityBetween(UUID activityId, LocalDateTime from, LocalDateTime to);

    @Query(value="from ActivityEntryEntity ae where ae.userId = :userId and ae.activity.id = :activityId and ae.startTime > :from and ae.startTime < :to")
    List<ActivityEntryEntity> findUsersActivityEntriesForActivityBetween(UUID userId, UUID activityId, LocalDateTime from, LocalDateTime to);

    /**
     * finds all activity entries for specified project and start time between specified boundaries. results are ordered by start time 
     * @param project the project which the activity entry belong to  ( project 1->n activity 1->n activity entry)
     * @param from optional: the oldest start time that shall be included in the results
     * @param to optional: the latest (most recent) start time that shall be included in the results
     * @return
     */
    @Query(value="Select ae from ActivityEntryEntity ae where ae.activity.project = :project and (:from is null or ae.startTime > :from) and (:to is null or ae.startTime < :to) order by ae.startTime")
    List<ActivityEntryEntity> findActivityEntriesForProjectBetween(ProjectEntity project, LocalDateTime from, LocalDateTime to);

    @Query(value="Select ae from ActivityEntryEntity ae where ae.userId = :userId and ae.activity.project = :project and (:from is null or ae.startTime > :from) and (:to is null or ae.startTime < :to) order by ae.startTime")
    List<ActivityEntryEntity> findUsersActivityEntriesForProjectBetween(UUID userId, ProjectEntity project, LocalDateTime from, LocalDateTime to);


    /**
     * finds all activity entries for specified client and start time between specified boundaries. results are ordered by start time 
     * @param client the client which the activity entry belongs to (client 1->n project 1->n activity 1->n activity entry)
     * @param from optional: the oldest start time that shall be included in the results
     * @param to optional: the latest (most recent) start time that shall be included in the results
     * @return
     */
    @Query(value="Select ae from ActivityEntryEntity ae where ae.activity.project.client = :client and (:from is null or ae.startTime > :from) and (:to is null or ae.startTime < :to) order by ae.startTime")
    List<ActivityEntryEntity> findActivityEntriesForClientBetween(ClientEntity client, LocalDateTime from, LocalDateTime to);
    
    @Query(value="Select ae from ActivityEntryEntity ae where ae.userId = :userId and ae.activity.project.client = :client and (:from is null or ae.startTime > :from) and (:to is null or ae.startTime < :to) order by ae.startTime")
    List<ActivityEntryEntity> findUsersActivityEntriesForClientBetween(UUID userId, ClientEntity client, LocalDateTime from, LocalDateTime to);
    
    List<ActivityEntryEntity> findAllByActivityId(UUID taskId);
    List<ActivityEntryEntity> findAll();
    List<ActivityEntryEntity> findAllByOrderByCreationTimeDesc(Pageable pageable);
    List<ActivityEntryEntity> findAllByOrderByStartTimeDesc(Pageable pageable);
    List<ActivityEntryEntity> findAllByOrderByCreationTimeAsc(Pageable pageable);
}
