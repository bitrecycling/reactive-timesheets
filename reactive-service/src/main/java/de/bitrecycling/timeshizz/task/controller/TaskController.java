package de.bitrecycling.timeshizz.task.controller;

import de.bitrecycling.timeshizz.task.model.Task;
import de.bitrecycling.timeshizz.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The task controller provides the endpoints to the task resource
 *
 * created by robo
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

    @PostMapping("/{id}")
    public Mono<Void> delete(@PathVariable("{id}") String taskId){
        return taskService.delete(taskId);
    }
}
