package de.bitrecycling.timeshizz.activity.controller;

import de.bitrecycling.timeshizz.activity.model.ActivityEntity;
import de.bitrecycling.timeshizz.activity.model.ActivityJson;
import de.bitrecycling.timeshizz.activity.model.ActivityMapper;
import de.bitrecycling.timeshizz.activity.service.ActivityService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * REST controller for the activity resource. provides the usual CRUD-like operations in a restful manner.
 * <p>
 * created by robo
 */
@RestController
@RequestMapping("/activities")
@Api(value = "Activity Management")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;
    private final ActivityMapper activityMapper;

    /**
     * get all activities, can be used for reports or custom filtering
     *
     * @return
     */
    @GetMapping
    public List<ActivityEntity> all() {
        return activityService.all();
    }

    /**
     * get all activity for given project
     *
     * @param projectId
     * @return
     */
    @GetMapping(params = "projectId")
    public List<ActivityEntity> allByProjectId(@RequestParam("projectId") UUID projectId) {
        return activityService.allByProjectId(projectId);
    }

    /**
     * get activity for given id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ActivityJson byId(@PathVariable("id") UUID id) {
        return activityMapper.toJson(activityService.byId(id));
    }

    /**
     * find all activities with creation datetime between the from and to datetimes.
     *
     * @param fromString a string representation of a date like 2018-11-05T17:08:42.477Z
     * @param toString   a string representation of a date like 2018-11-05T17:08:42.477Z
     * @return
     */
    @GetMapping(params = {"from", "to"})
    public List<ActivityEntity> allByCreationTime(@RequestParam("from") String fromString, @RequestParam("to") String toString) {
        LocalDateTime from = LocalDateTime.parse(fromString);
        LocalDateTime to = LocalDateTime.parse(toString);
        return activityService.findByCreationTimeBetween(from, to);
    }


    @PostMapping(consumes = "application/json")
    public ActivityEntity create(@RequestBody ActivityJson activity) {
        return activityService.insert(activity.getName(), activity.getProjectId());
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id) {
        activityService.delete(id);
    }
}
