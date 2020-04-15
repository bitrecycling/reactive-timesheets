package de.bitrecycling.timeshizz.activity.service;

import de.bitrecycling.timeshizz.activity.model.ActivityEntity;
import de.bitrecycling.timeshizz.activity.model.ActivityEntryEntity;
import de.bitrecycling.timeshizz.activity.repository.ActivityEntryRepository;
import de.bitrecycling.timeshizz.activity.repository.ActivityRepository;
import de.bitrecycling.timeshizz.client.repository.ClientRepository;
import de.bitrecycling.timeshizz.project.repository.ProjectRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The activity entry service
 * created by robo
 */
@Service
public class ActivityEntryService {

    @Autowired
    private ActivityEntryRepository activityEntryRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ProjectRespository projectRespository;
    @Autowired
    private ClientRepository clientRepository;

    public List<ActivityEntryEntity> all() {
        return activityEntryRepository.findAll();
    }

    public List<ActivityEntryEntity> getAllById(UUID id) {
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

    public List<ActivityEntryEntity> getPagedAscending(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return activityEntryRepository.findAllByOrderByCreationTimeAsc(pageable);
    }

    public List<ActivityEntryEntity> getPagedDescending(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return activityEntryRepository.findAllByOrderByCreationTimeDesc(pageable);

    }

    public ActivityEntryEntity createForActivity(ActivityEntryEntity activityEntry, UUID activityId) {
        final ActivityEntity activity = activityRepository.findById(activityId).orElseThrow(() -> new RuntimeException(String.format("activity[$] not found", activityId)));
        activityEntry.setActivity(activity);
        return activityEntryRepository.save(activityEntry);
    }

    public ActivityEntryEntity byId(UUID id) {
        return activityEntryRepository.findById(id).orElseThrow(()->new RuntimeException("ActivityEntry["+id+"] not available"));
    }
    
    public ActivityEntryEntity save(ActivityEntryEntity taskEntry) {
        return activityEntryRepository.save(taskEntry);
    }

    public void delete(UUID taskEntryId) {
        activityEntryRepository.deleteById(taskEntryId);
    }

}
