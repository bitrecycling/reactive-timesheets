package de.bitrecycling.timeshizz.project.controller;

import de.bitrecycling.timeshizz.project.model.Project;
import de.bitrecycling.timeshizz.project.service.ProjectService;
import de.bitrecycling.timeshizz.task.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The client controller provides the endpoints to the client resource
 *
 * created by robo
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
        return projectService.addTask(projectId, taskName);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("{id}") String projectId){
        return projectService.delete(projectId);
    }
}
