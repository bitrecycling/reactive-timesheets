package de.bitrecycling.timeshizz.controller;

import de.bitrecycling.timeshizz.model.Task;
import de.bitrecycling.timeshizz.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public Mono<Task> create(String taskName, String projectId){
        Task task = Task.builder().name(taskName).projectId(projectId).build();
        return taskService.insert(task);
    }
}
