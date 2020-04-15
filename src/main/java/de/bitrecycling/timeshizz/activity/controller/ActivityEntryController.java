package de.bitrecycling.timeshizz.activity.controller;

import de.bitrecycling.timeshizz.activity.model.ActivityEntryEntity;
import de.bitrecycling.timeshizz.activity.model.ActivityEntryJson;
import de.bitrecycling.timeshizz.activity.model.ActivityMapper;
import de.bitrecycling.timeshizz.activity.service.ActivityEntryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST controller for the activity entry resource. provides the usual CRUD-like operations in a restful manner.
 * <p>
 * created by robo
 */
@RestController
@RequestMapping("/activityentries")
@Api(value = "ActivityEntry Management", description = "CRUD for activity entry resource")
public class ActivityEntryController {

    @Autowired
    private ActivityEntryService activityEntryService;
    
    @Autowired
    private ActivityMapper activityMapper;

    @GetMapping
    public List<ActivityEntryEntity> all() {
        return activityEntryService.all();
    }

    @GetMapping(params = "activityId")
    public List<ActivityEntryEntity> allByActivityId(@RequestParam("activityId") UUID activityId) {
        return activityEntryService.getAllById(activityId);
    }
    

    @PostMapping(consumes = "application/json")
    public ActivityEntryJson createByJson(@RequestBody ActivityEntryJson activityEntry) {
        return activityMapper.toJson(activityEntryService.createForActivity(activityMapper.toEntity(activityEntry), activityEntry.getActivityId()));

    }
    

    @PutMapping(consumes = "application/json")
    public ActivityEntryJson saveByJson(@RequestBody ActivityEntryEntity activityEntry) {
        return activityMapper.toJson(activityEntryService.save(activityEntry));
    }


    /**
     * find all tasks with creation datetime between the from and to datetimes.
     *
     * @param fromString a ISO string representation of a date like 2018-11-05T17:08:42.477Z
     * @param toString   a ISO string representation of a date like 2018-11-05T17:08:42.477Z
     * @return
     */
    @GetMapping(params = {"from", "to"})
    public List<ActivityEntryJson> allByCreationTime(@RequestParam("from") String fromString, @RequestParam("to") String toString) {
        LocalDateTime from = LocalDateTime.parse(fromString);
        LocalDateTime to = LocalDateTime.parse(toString);
        return activityEntryService.getByCreationTimeBetween(from, to).stream().map(t-> activityMapper.toJson(t)).collect(Collectors.toList());
    }

    /**
     * find all activityentries with start datetime between the from and to datetimes.
     *
     * @param start a ISO string representation of a date like 2018-11-05T17:08:42.477Z
     * @param until a ISO string representation of a date like 2018-11-05T17:08:42.477Z
     * @return
     */
    @GetMapping(params = {"start", "until"})
    public List<ActivityEntryJson> allByStartTime(@RequestParam("start") String start, @RequestParam("until") String until) {
        LocalDateTime from = LocalDateTime.parse(start);
        LocalDateTime to = LocalDateTime.parse(until);
        return activityEntryService.getByStartTimeBetween(from, to).stream().map(t-> activityMapper.toJson(t)).collect(Collectors.toList());
    }

    /**
     * find all activityentries with start datetime between the from and to datetimes.
     *
     * @param start a ISO string representation of a date like 2018-11-05T17:08:42.477Z
     * @param until a ISO string representation of a date like 2018-11-05T17:08:42.477Z
     * @return
     */
    @GetMapping(params = {"start", "until", "taskId"})
    public List<ActivityEntryJson> allByStartTime(@RequestParam("start") String start,
                                                  @RequestParam("until") String until,
                                                  @RequestParam("taskId") UUID taskId) {
        LocalDateTime from = LocalDateTime.parse(start);
        LocalDateTime to = LocalDateTime.parse(until);
        return activityEntryService.getByStartTimeBetween(from, to, taskId).stream().map(t-> activityMapper.toJson(t)).collect(Collectors.toList());
    }

    /**
     * find n most recent tasks
     *
     * @param count an integer indicating how many recent tasks shall be returned
     * @return
     */
    @GetMapping(params = {"count"})
    public List<ActivityEntryJson> recentByStartTime(@RequestParam("count") Integer count) {
        return activityEntryService.getMostRecentByStartTime(count).stream().map(t-> activityMapper.toJson(t)).collect(Collectors.toList());
    }


    /**
     * find n tasks with creation datetime ordered ascending
     *
     * @param page  the page number to load
     * @param size  the page size to load
     * @param order either "asc" or "dsc" the order of the taskentries
     * @return
     */
    @GetMapping(params = {"order", "page", "size"})
    public List<ActivityEntryJson> allByCreationTimeOrdered(@RequestParam("order") String order,
                                                            @RequestParam("page") Integer page,
                                                            @RequestParam("size") Integer size) {
        if ("asc".equals(order)) {
            return activityEntryService.getPagedAscending(page, size).stream().map(t-> activityMapper.toJson(t)).collect(Collectors.toList());
        }
        return activityEntryService.getPagedDescending(page, size).stream().map(t-> activityMapper.toJson(t)).collect(Collectors.toList());
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID taskEntryId) {
        activityEntryService.delete(taskEntryId);
    }


}
