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

    @GetMapping(params = "projectId")
    public Flux<Task> allByProjectId(@RequestParam("projectId") String projectId){
        return taskService.allByProjectId(projectId);
    }

    @PostMapping
    public Mono<Task> create(@RequestParam("name") String taskName, @RequestParam("projectId") String projectId){
        Task task = Task.builder().name(taskName).projectId(projectId).build();
        return taskService.insert(task);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String taskId){
        return taskService.delete(taskId);
    }
}
