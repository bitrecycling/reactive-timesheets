package de.bitrecycling.timeshizz.activity.service;

import de.bitrecycling.timeshizz.activity.model.ActivityEntity;
import de.bitrecycling.timeshizz.activity.repository.ActivityRepository;
import de.bitrecycling.timeshizz.client.repository.ClientRepository;
import de.bitrecycling.timeshizz.common.ResourceNotFoundException;
import de.bitrecycling.timeshizz.project.model.ProjectEntity;
import de.bitrecycling.timeshizz.project.repository.ProjectRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The activity service
 * created by robo
 */
@Service
public class ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    ProjectRespository projectRespository;

    @Autowired
    ClientRepository clientRepository;

    public List<ActivityEntity> all() {
        return (List<ActivityEntity>) activityRepository.findAll();
    }

    public ActivityEntity byId(UUID activityId){
        return activityRepository.findById(activityId).orElseThrow(()->new ResourceNotFoundException("activity", activityId.toString()));
    }

    public List<ActivityEntity> allByProjectId(UUID projectId) {
        return activityRepository.findAllByProjectIdOrderByCreationTimeDesc(projectId);
    }

    public ActivityEntity insert(String activityName, UUID projectId) {
        ActivityEntity activity = new ActivityEntity();
        final ProjectEntity project = projectRespository.findById(projectId).orElseThrow(()->new RuntimeException(String.format("project[$] not found", projectId)));
        activity.setName(activityName);
        activity.setProject(project);
        activity.setCreationTime(LocalDateTime.now());
        activityRepository.save(activity);
        return activity;
    }

    public void delete(UUID taskId) {
        activityRepository.deleteById(taskId);
    }

    public List<ActivityEntity> findByCreationTimeBetween(LocalDateTime from, LocalDateTime to) {
        return activityRepository.findByCreationTimeBetween(from, to);
    }

    /**
     *
     * @param task
     * @return
     */
    public ActivityEntity save(ActivityEntity task) {
        return activityRepository.save(task);
    }

    /**
     * saves only possible change: taskName
     * project and client cannot be changed
     * @param id
     * @param name
     * @return
     */
    public ActivityEntity save(UUID id, String name) {
        final ActivityEntity activity= byId(id);
        activity.setName(name);
        return activityRepository.save(activity);
    }
}
