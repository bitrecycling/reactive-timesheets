package de.bitrecycling.timeshizz.task.controller;

import de.bitrecycling.timeshizz.task.model.TaskEntryEntity;
import de.bitrecycling.timeshizz.task.model.TaskEntryJson;
import de.bitrecycling.timeshizz.task.model.TaskMapper;
import de.bitrecycling.timeshizz.task.service.TaskEntryService;
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
 * REST controller for the task entry resource. provides the usual CRUD-like operations in a restful manner.
 * <p>
 * created by robo
 */
@RestController
@RequestMapping("/taskentries")
@Api(value = "TaskEntry Management", description = "CRUD for task entry resource")
public class TaskEntryController {

    @Autowired
    private TaskEntryService taskEntryService;
    
    @Autowired
    private TaskMapper taskMapper;

    @GetMapping
    public List<TaskEntryEntity> all() {
        return taskEntryService.all();
    }

    @GetMapping(params = "taskId")
    public List<TaskEntryEntity> allByTaskId(@RequestParam("taskId") UUID taskId) {
        return taskEntryService.getAllByTaskId(taskId);
    }
    

    @PostMapping(consumes = "application/json")
    public TaskEntryJson createByJson(@RequestBody TaskEntryJson taskEntry) {
        return taskMapper.toJson(taskEntryService.insert(taskMapper.toEntity(taskEntry)));

    }
    

    @PutMapping(consumes = "application/json")
    public TaskEntryJson saveByJson(@RequestBody TaskEntryEntity taskEntry) {
        return taskMapper.toJson(taskEntryService.save(taskEntry));
    }


    /**
     * find all tasks with creation datetime between the from and to datetimes.
     *
     * @param fromString a ISO string representation of a date like 2018-11-05T17:08:42.477Z
     * @param toString   a ISO string representation of a date like 2018-11-05T17:08:42.477Z
     * @return
     */
    @GetMapping(params = {"from", "to"})
    public List<TaskEntryJson> allByCreationTime(@RequestParam("from") String fromString, @RequestParam("to") String toString) {
        LocalDateTime from = LocalDateTime.parse(fromString);
        LocalDateTime to = LocalDateTime.parse(toString);
        return taskEntryService.getByCreationTimeBetween(from, to).stream().map(t->taskMapper.toJson(t)).collect(Collectors.toList());
    }

    /**
     * find all task entries with start datetime between the from and to datetimes.
     *
     * @param start a ISO string representation of a date like 2018-11-05T17:08:42.477Z
     * @param until a ISO string representation of a date like 2018-11-05T17:08:42.477Z
     * @return
     */
    @GetMapping(params = {"start", "until"})
    public List<TaskEntryJson> allByStartTime(@RequestParam("start") String start, @RequestParam("until") String until) {
        LocalDateTime from = LocalDateTime.parse(start);
        LocalDateTime to = LocalDateTime.parse(until);
        return taskEntryService.getByStartTimeBetween(from, to).stream().map(t->taskMapper.toJson(t)).collect(Collectors.toList());
    }

    /**
     * find all task entries with start datetime between the from and to datetimes.
     *
     * @param start a ISO string representation of a date like 2018-11-05T17:08:42.477Z
     * @param until a ISO string representation of a date like 2018-11-05T17:08:42.477Z
     * @return
     */
    @GetMapping(params = {"start", "until", "taskId"})
    public List<TaskEntryJson> allByStartTime(@RequestParam("start") String start,
                                                @RequestParam("until") String until,
                                                @RequestParam("taskId") UUID taskId) {
        LocalDateTime from = LocalDateTime.parse(start);
        LocalDateTime to = LocalDateTime.parse(until);
        return taskEntryService.getByStartTimeBetween(from, to, taskId).stream().map(t->taskMapper.toJson(t)).collect(Collectors.toList());
    }

    /**
     * find n most recent tasks
     *
     * @param count an integer indicating how many recent tasks shall be returned
     * @return
     */
    @GetMapping(params = {"count"})
    public List<TaskEntryJson> recentByStartTime(@RequestParam("count") Integer count) {
        return taskEntryService.getMostRecentByStartTime(count).stream().map(t->taskMapper.toJson(t)).collect(Collectors.toList());
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
    public List<TaskEntryJson> allByCreationTimeOrdered(@RequestParam("order") String order,
                                                          @RequestParam("page") Integer page,
                                                          @RequestParam("size") Integer size) {
        if ("asc".equals(order)) {
            return taskEntryService.getPagedAscending(page, size).stream().map(t->taskMapper.toJson(t)).collect(Collectors.toList());
        }
        return taskEntryService.getPagedDescending(page, size).stream().map(t->taskMapper.toJson(t)).collect(Collectors.toList());
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID taskEntryId) {
        taskEntryService.delete(taskEntryId);
    }


}
