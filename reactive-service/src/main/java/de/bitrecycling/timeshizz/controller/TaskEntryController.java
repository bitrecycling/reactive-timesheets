package de.bitrecycling.timeshizz.controller;

import de.bitrecycling.timeshizz.model.TaskEntry;
import de.bitrecycling.timeshizz.service.TaskEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The task controller provides the endpoints to the task resource
 *
 * creationTime by robo
 */
@RestController
@RequestMapping("/taskentries")
public class TaskEntryController {

    @Autowired
    private TaskEntryService taskEntryService;

    @GetMapping
    public Flux<TaskEntry> all(){
        return taskEntryService.all();
    }

    @PostMapping
    public Mono<TaskEntry> create(@RequestBody TaskEntry taskEntry){
        return taskEntryService.insert(taskEntry);

    }
}
