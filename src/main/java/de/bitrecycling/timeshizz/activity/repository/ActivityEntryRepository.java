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
    
    // https://www.baeldung.com/jpa-join-types#multiple
    // https://thoughts-on-java.org/spring-data-jpa-query-annotation/
    // https://www.oracle.com/technical-resources/articles/vasiliev-jpql.html
    
    @Query(value="from ActivityEntryEntity ae where ae.activity.id = :activityId and ae.startTime > :from and ae.startTime < :to")
    List<ActivityEntryEntity> findActivityEntriesForActivityBetween(UUID activityId, LocalDateTime from, LocalDateTime to);
    @Query(value="Select ae from ActivityEntryEntity ae where ae.activity.project = :project and ae.startTime > :from and ae.startTime < :to")
    List<ActivityEntryEntity> findActivityEntriesForProjectBetween(ProjectEntity project, LocalDateTime from, LocalDateTime to);

    @Query(value="Select ae from ActivityEntryEntity ae where ae.activity.project.client = :client and ae.startTime > :from and ae.startTime < :to")
    List<ActivityEntryEntity> findActivityEntriesForClientBetween(ClientEntity client, LocalDateTime from, LocalDateTime to);
    
    List<ActivityEntryEntity> findAllByActivityId(UUID taskId);
    List<ActivityEntryEntity> findAll();
    List<ActivityEntryEntity> findAllByOrderByCreationTimeDesc(Pageable pageable);
    List<ActivityEntryEntity> findAllByOrderByStartTimeDesc(Pageable pageable);
    List<ActivityEntryEntity> findAllByOrderByCreationTimeAsc(Pageable pageable);
}
