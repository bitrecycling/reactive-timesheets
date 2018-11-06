package de.bitrecycling.timeshizz.project.controller;

import de.bitrecycling.timeshizz.project.model.Project;
import de.bitrecycling.timeshizz.project.service.ProjectService;
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

    @GetMapping("/{id}")
    public Mono<Project> byId(@PathVariable("id") String id){
        return projectService.byId(id);
    }

    @GetMapping(params = "clientId")
    public Flux<Project> allByClientId(@RequestParam("clientId") String clientId){
        return projectService.allByClientId(clientId);
    }

    @PostMapping
    public Mono<Project> create(@RequestParam("name") String projectName,
                                @RequestParam("description") String projectDescription,
                                @RequestParam("clientId") String clientId){
        Project project = Project.builder().name(projectName).description(projectDescription).clientId(clientId).build();
        return projectService.create(project);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String projectId){
        return projectService.delete(projectId);
    }
}
