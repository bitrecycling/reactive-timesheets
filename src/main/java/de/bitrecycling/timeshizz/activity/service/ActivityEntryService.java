package de.bitrecycling.timeshizz.activity.service;

import de.bitrecycling.timeshizz.activity.model.ActivityEntity;
import de.bitrecycling.timeshizz.activity.model.ActivityEntryEntity;
import de.bitrecycling.timeshizz.activity.repository.ActivityEntryRepository;
import de.bitrecycling.timeshizz.activity.repository.ActivityRepository;
import de.bitrecycling.timeshizz.client.model.ClientEntity;
import de.bitrecycling.timeshizz.client.repository.ClientRepository;
import de.bitrecycling.timeshizz.common.ResourceNotFoundException;
import de.bitrecycling.timeshizz.project.model.ProjectEntity;
import de.bitrecycling.timeshizz.project.repository.ProjectRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * TODO: guarding to allow normal (logged-in) users only to deal with their owned items
 * The activity entry service
 * created by robo
 */
@Service
@RequiredArgsConstructor
public class ActivityEntryService {

    private final ActivityEntryRepository activityEntryRepository;
    private final ActivityRepository activityRepository;
    private final ProjectRespository projectRespository;
    private final ClientRepository clientRepository;

    public List<ActivityEntryEntity> all() {

        return activityEntryRepository.findAll();
    }

    public List<ActivityEntryEntity> getAllByActivityId(UUID id) {
        activityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("activity", id));
        return activityEntryRepository.findAllByActivityIdOrderByCreationTimeDesc(id);
    }

    public List<ActivityEntryEntity> getMostRecentByStartTime(Integer count) {
        PageRequest of = PageRequest.of(0, count);
        return activityEntryRepository.findAllByOrderByStartTimeDesc(of);
    }

    public List<ActivityEntryEntity> getByCreationTimeBetween(LocalDateTime from, LocalDateTime to) {
        return activityEntryRepository.findAllByCreationTimeBetween(from, to);
    }

    public List<ActivityEntryEntity> getByStartTimeBetween(LocalDateTime from, LocalDateTime to) {
        return activityEntryRepository.findAllByStartTimeBetween(from, to);
    }
    
    public List<ActivityEntryEntity> getByStartTimeBetween(LocalDateTime from, LocalDateTime to, UUID taskId) {
        return activityEntryRepository.findAllByActivityIdAndStartTimeBetween(taskId, from, to);
    }

    /**
     * retrieve all activity entries belonging to a specified client, start time can be restricted. ordered by start time
     *
     * This can be useful to generate monthly reports.
     *
     * @param clientId the client to retrieve the activity entries for
     * @param from the earliest start date of the activity entries to select
     * @param to the latest start date of the activity entries to select
     */           
    public List<ActivityEntryEntity> getForClientByStartTimeBetween(UUID clientId, LocalDateTime from, LocalDateTime to) {
        final ClientEntity client = clientRepository.findById(clientId).orElseThrow(()->new ResourceNotFoundException(String.format("client [%s] not found", clientId)));
        return activityEntryRepository.findActivityEntriesForClientBetween(client, from, to);
    }

    /**
     * retrieve all activity entries belonging to a specified client, start time can be restricted. ordered by start time
     *
     * This can be useful to generate monthly reports.
     *
     * @param projectId the project to retrieve the activity entries for
     * @param from the earliest start date of the activity entries to select
     * @param to the latest start date of the activity entries to select
     */
    public List<ActivityEntryEntity> getForProjectByStartTimeBetween(UUID projectId, LocalDateTime from, LocalDateTime to) {
        final ProjectEntity project = projectRespository.findById(projectId).orElseThrow(()->new ResourceNotFoundException(String.format("project [%s] not found", projectId)));
        return activityEntryRepository.findActivityEntriesForProjectBetween(project, from, to);
    }

    /**
     * retrieve all activity entries belonging to a specified client, start time can be restricted. ordered by start time
     * 
     * This can be useful to generate monthly reports.
     * 
     * @param clientId the client to retrieve the activity entries for
     * @param from the earliest start date of the activity entries to select
     * @param to the latest start date of the activity entries to select
     * @param grouping maybe later
     * @return
     */
    public Map<String, List<ActivityEntryEntity>> getGroupedForClientByStartTimeBetween(UUID clientId, LocalDateTime from, LocalDateTime to, String grouping) {
        final ClientEntity client = clientRepository.findById(clientId).orElseThrow(()->new ResourceNotFoundException(String.format("client [%s] not found", clientId)));
        return activityEntryRepository.findActivityEntriesForClientBetween(client, from, to).stream().collect(Collectors.groupingBy(y->y.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE), Collectors.toList()));
    }

    /**
     *
     * retrieve all activity entries belonging to a specified project, start time can be restricted. ordered by start time
     * This can be useful to generate monthly reports.
     * 
     * @param projectId the project to retrieve the activity entries for
     * @param from the earliest start date of the activity entries to select
     * @param to the latest start date of the activity entries to select
     * @param grouping maybe later
     * @return
     */
    public Map<String, List<ActivityEntryEntity>> getGroupedForProjectByStartTimeBetween(UUID projectId, LocalDateTime from, LocalDateTime to, String grouping) {
        final ProjectEntity project = projectRespository.findById(projectId).orElseThrow(()->new ResourceNotFoundException(String.format("project [%s] not found", projectId)));
        return activityEntryRepository.findActivityEntriesForProjectBetween(project, from, to).stream().collect(Collectors.groupingBy(y->y.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE), Collectors.toList()));
    }
    
    public List<ActivityEntryEntity> getPagedAscending(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return activityEntryRepository.findAllByOrderByCreationTimeAsc(pageable);
    }

    public List<ActivityEntryEntity> getPagedDescending(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return activityEntryRepository.findAllByOrderByCreationTimeDesc(pageable);

    }

    public ActivityEntryEntity createForActivity(ActivityEntryEntity activityEntry, UUID activityId) {
        final ActivityEntity activity = activityRepository.findById(activityId).orElseThrow(() -> new ResourceNotFoundException(String.format("activity[%s] not found", activityId)));
        activityEntry.setActivity(activity);
        return activityEntryRepository.save(activityEntry);
    }

    public ActivityEntryEntity byId(UUID id) {
        return activityEntryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("ActivityEntry["+id+"] not available"));
    }
    
    public ActivityEntryEntity save(ActivityEntryEntity taskEntry) {
        return activityEntryRepository.save(taskEntry);
    }

    public void delete(UUID taskEntryId) {
        activityEntryRepository.deleteById(taskEntryId);
    }

}
