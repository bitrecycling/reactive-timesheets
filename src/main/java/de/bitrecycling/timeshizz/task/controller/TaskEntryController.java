package de.bitrecycling.timeshizz.task.controller;

import de.bitrecycling.timeshizz.common.controller.ControllerUtils;
import de.bitrecycling.timeshizz.task.model.TaskEntry;
import de.bitrecycling.timeshizz.task.service.TaskEntryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The task controller provides the endpoints to the task resource
 * <p>
 * created by robo
 */
@RestController
@RequestMapping("/taskentries")
@Api(value = "TaskEntry Management", description = "CRUD for task entry resource")
public class TaskEntryController {

    @Autowired
    private TaskEntryService taskEntryService;

    @GetMapping
    public Flux<TaskEntry> all() {
        return taskEntryService.all();
    }

    @GetMapping(params = "taskId")
    public Flux<TaskEntry> allByTaskId(@RequestParam("taskId") String taskId) {
        return taskEntryService.getAllByTaskId(taskId);
    }

    /**
     * @param startTimeString provide a natural language time string, like "15:23" or "in 5 minutes"
     *                        or "2018-11-23 12:34". thanks to ocpsoft.prettytime!
     * @param durationMinutes
     * @param taskId
     * @return
     */
    @PostMapping(consumes = "application/x-www-form-urlencoded")
    public Mono<TaskEntry> createByUrlParams(@RequestParam("startTime") String startTimeString, @RequestParam("durationMinutes") Integer durationMinutes, @RequestParam("taskId") String taskId) {
        LocalDateTime parsedTime = ControllerUtils.parseTime(startTimeString);
        TaskEntry taskEntry = new TaskEntry(parsedTime, durationMinutes, taskId);
        return taskEntryService.insert(taskEntry);

    }

    @PostMapping(consumes = "application/json")
    public Mono<TaskEntry> createByJson(@RequestBody TaskEntry taskEntry) {
        return taskEntryService.insert(taskEntry);

    }

    @PutMapping(name = "{id}", consumes = "application/x-www-form-urlencoded")
    public Mono<TaskEntry> saveByUrlParams(@RequestParam("id") String id, @RequestParam("startTime") String startTimeString, @RequestParam("durationMinutes") Integer durationMinutes, @RequestParam("taskId") String taskId) {
        LocalDateTime parsedTime = ControllerUtils.parseTime(startTimeString);
        TaskEntry taskEntry = new TaskEntry(parsedTime, durationMinutes, taskId);
        taskEntry.setId(id);
        return taskEntryService.save(taskEntry);

    }

    @PutMapping(name = "{id}", consumes = "application/json")
    public Mono<TaskEntry> saveByJson(@RequestParam("id") String id, @RequestBody TaskEntry taskEntry) {
        if (!ControllerUtils.consistent(id, taskEntry)) {
            throw new RuntimeException("Error: path id and json id are not equal:[" + id + " vs " + taskEntry.getId() + "]");
        }
        return taskEntryService.save(taskEntry);

    }


    /**
     * find all tasks with creation datetime between the from and to datetimes.
     *
     * @param fromString a ISO string representation of a date like 2018-11-05T17:08:42.477Z
     * @param toString   a ISO string representation of a date like 2018-11-05T17:08:42.477Z
     * @return
     */
    @GetMapping(params = {"from", "to"})
    public Flux<TaskEntry> allByCreationTime(@RequestParam("from") String fromString, @RequestParam("to") String toString) {
        LocalDateTime from = LocalDateTime.parse(fromString);
        LocalDateTime to = LocalDateTime.parse(toString);
        return taskEntryService.getByCreationTimeBetween(from, to);
    }

    /**
     * find all tasks with start datetime between the from and to datetimes.
     *
     * @param start a ISO string representation of a date like 2018-11-05T17:08:42.477Z
     * @param until a ISO string representation of a date like 2018-11-05T17:08:42.477Z
     * @return
     */
    @GetMapping(params = {"start", "until"})
    public Flux<TaskEntry> allByStartTime(@RequestParam("start") String start, @RequestParam("until") String until) {
        LocalDateTime from = LocalDateTime.parse(start);
        LocalDateTime to = LocalDateTime.parse(until);
        return taskEntryService.getByStartTimeBetween(from, to);
    }

    /**
     * find n most recent tasks
     *
     * @param count an integer indicating how many recent tasks shall be returned
     * @return
     */
    @GetMapping(params = {"count"})
    public Flux<TaskEntry> recentByStartTime(@RequestParam("count") Integer count) {
        return taskEntryService.getMostRecentByStartTime(count);
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
    public Flux<TaskEntry> allByCreationTimeOrdered(@RequestParam("order") String order,
                                                    @RequestParam("page") Integer page,
                                                    @RequestParam("size") Integer size) {
        if ("asc".equals(order)) {
            return taskEntryService.getPagedAscending(page, size);
        }
        return taskEntryService.getPagedDescending(page, size);
    }


    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String taskEntryId) {
        return taskEntryService.delete(taskEntryId);
    }


}
