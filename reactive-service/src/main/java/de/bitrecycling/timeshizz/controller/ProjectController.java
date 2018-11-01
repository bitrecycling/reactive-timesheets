package de.bitrecycling.timeshizz.controller;

import de.bitrecycling.timeshizz.model.Project;
import de.bitrecycling.timeshizz.model.Task;
import de.bitrecycling.timeshizz.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The client controller provides the endpoints to the client resource
 *
 * creationTime by robo
 */
@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping
    public Flux<Project> all(){
        return projectService.all();
    }

    @PostMapping
    public Mono<Project> create(@RequestBody Project project){
        return projectService.create(project);
    }

    @PutMapping("/{id}")
    public Mono<Task> addTask(@PathVariable("{id}") String projectId, String taskName){
        Task task = Task.builder().projectId(projectId).name(taskName).build();
        return projectService.addTask(task);
    }
}
