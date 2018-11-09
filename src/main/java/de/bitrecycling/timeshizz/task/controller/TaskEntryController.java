package de.bitrecycling.timeshizz.task.controller;

import de.bitrecycling.timeshizz.task.model.Task;
import de.bitrecycling.timeshizz.task.model.TaskEntry;
import de.bitrecycling.timeshizz.task.service.TaskEntryService;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * The task controller provides the endpoints to the task resource
 * <p>
 * created by robo
 */
@RestController
@RequestMapping("/taskentries")
public class TaskEntryController {

    @Autowired
    private TaskEntryService taskEntryService;

    @GetMapping
    public Flux<TaskEntry> all() {
        return taskEntryService.all();
    }

    @GetMapping(params = "taskId")
    public Flux<TaskEntry> allByTaskId(@RequestParam("taskId") String taskId) {
        return taskEntryService.allByTaskId(taskId);
    }

    /**
     *
     * @param startTimeString provide a natural language time string, like "15:23" or "in 5 minutes"
     *                        or "2018-11-23 12:34". thanks to ocpsoft.prettytime!
     * @param durationMinutes
     * @param taskId
     * @return
     */
    @PostMapping
    public Mono<TaskEntry> create(@RequestParam("startTime") String startTimeString, @RequestParam("durationMinutes") Integer durationMinutes, @RequestParam("taskId") String taskId) {
        PrettyTimeParser prettyTimeParser = new PrettyTimeParser();
        List<Date> parse = prettyTimeParser.parse(startTimeString);
        LocalDateTime localDateTime = parse.get(0).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        TaskEntry taskEntry = new TaskEntry(localDateTime,durationMinutes,taskId);
        return taskEntryService.insert(taskEntry);

    }

    /**
     * find all tasks with creation datetime between the from and to datetimes.
     *
     * @param fromString a string representation of a date like 2018-11-05T17:08:42.477Z
     * @param toString a string representation of a date like 2018-11-05T17:08:42.477Z
     * @return
     */
    @GetMapping(params = {"from", "to"})
    public Flux<TaskEntry> allByCreationTime(@RequestParam("from") String fromString, @RequestParam("to") String toString) {
        LocalDateTime from = LocalDateTime.parse(fromString);
        LocalDateTime to = LocalDateTime.parse(toString);
        return taskEntryService.findByCreationTimeBetween(from, to);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String taskEntryId) {
        return taskEntryService.delete(taskEntryId);
    }
}
