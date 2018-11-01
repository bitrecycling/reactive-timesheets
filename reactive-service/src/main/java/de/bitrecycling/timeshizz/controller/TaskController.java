package de.bitrecycling.timeshizz.controller;

import de.bitrecycling.timeshizz.model.Task;
import de.bitrecycling.timeshizz.service.TaskService;
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
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public Flux<Task> all(){
        return taskService.all();
    }

    @PostMapping
    public Mono<Task> create(@RequestBody Task task){
        return taskService.insert(task);

    }
}
