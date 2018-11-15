package de.bitrecycling.timeshizz.project.controller;

import de.bitrecycling.timeshizz.project.model.Project;
import de.bitrecycling.timeshizz.project.service.ProjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The client controller provides the endpoints to the client resource
 * <p>
 * created by robo
 */
@RestController
@RequestMapping("/projects")
@Api(value = "Project Management", description = "CRUD for project resource")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping
    public Flux<Project> all() {
        return projectService.all();
    }

    @GetMapping("/{id}")
    public Mono<Project> byId(@PathVariable("id") String id) {
        return projectService.byId(id);
    }

    @GetMapping(params = "clientId")
    public Flux<Project> allByClientId(@RequestParam("clientId") String clientId) {
        return projectService.allByClientId(clientId);
    }

    @PostMapping(consumes = "application/x-www-form-urlencoded")
    public Mono<Project> create(@RequestParam("name") String projectName,
                                @RequestParam("description") String projectDescription,
                                @RequestParam("rate") Double rate,
                                @RequestParam("clientId") String clientId) {
        Project project = new Project(projectName, projectDescription, rate, clientId);
        return projectService.create(project);
    }

    @PostMapping(consumes = "application/json")
    public Mono<Project> create(@RequestBody Project project) {
        return projectService.create(project);
    }

    @PutMapping(value = "/{id}", consumes = "application/x-www-form-urlencoded")
    public Mono<Project> update(@RequestParam("id") String id,
                                @RequestParam("name") String projectName,
                                @RequestParam("description") String projectDescription,
                                @RequestParam("rate") Double rate,
                                @RequestParam("clientId") String clientId) {

        Project project = new Project(projectName, projectDescription, rate, clientId);
        project.setId(id);
        return projectService.save(project);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public Mono<Project> update(@PathVariable("id") String id, @RequestBody Project project) {
        if (!consistent(id, project)) {
            throw new RuntimeException("Error: path projectId and json clientId are not equal:[" + id + " vs " + project.getId() + "]");
        }
        return projectService.save(project);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String projectId) {
        return projectService.delete(projectId);
    }

    private boolean consistent(String id, Project project) {
        if (project.getId() != null) {
            return id.equals(project.getId());
        }
        return true;
    }
}
