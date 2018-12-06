package de.bitrecycling.timeshizz.project.controller;

import de.bitrecycling.timeshizz.common.ResourceNotFoundException;
import de.bitrecycling.timeshizz.common.controller.ControllerUtils;
import de.bitrecycling.timeshizz.project.model.Project;
import de.bitrecycling.timeshizz.project.service.ProjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for the project resource. provides the usual CRUD-like operations in a restful manner.
 * <p>
 * created by robo
 */
@RestController
@RequestMapping("/projects")
@Api(value = "Project Management", description = "CRUD for project resource")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    /**
     * get all projects
     * @return
     */
    @GetMapping
    public Flux<Project> all() {
        return projectService.all();
    }

    /**
     * get specific project
     * @param id the id of the project to load
     * @return
     */
    @GetMapping("/{id}")
    public Mono<Project> byId(@PathVariable("id") String id) {
        return projectService.byId(id);
    }

    /**
     * get projects for given client
     * @param clientId the client's id to return projects for
     * @return
     */
    @GetMapping(params = "clientId")
    public Flux<Project> allByClientId(@RequestParam("clientId") String clientId) {
        return projectService.allByClientId(clientId);
    }

    /**
     * get the number of projects for given client
     * @param clientId the client's id to return project count for
     * @return
     */
    @GetMapping(value = "/_count", params = "clientId")
    public Mono<Long> countAllByClientId(@RequestParam("clientId") String clientId) {
        return projectService.countAllByClientId(clientId);
    }

    /**
     * create a new project using url params.
     * @param projectName the name of the project to create
     * @param projectDescription the description of the project to create
     * @param rate the hourly rate (no currency as of now) of the project to create
     * @param clientId the client id the project is associated with
     * @return
     */
    @PostMapping(consumes = "application/x-www-form-urlencoded")
    public Mono<Project> create(@RequestParam("name") String projectName,
                                @RequestParam("description") String projectDescription,
                                @RequestParam("rate") Double rate,
                                @RequestParam("clientId") String clientId) {
        Project project = new Project(projectName, projectDescription, rate, clientId);
        return projectService.create(project);
    }

    /**
     * create a project using json
     * @param project the project as json to be created
     * @return
     */
    @PostMapping(consumes = "application/json")
    public Mono<Project> create(@RequestBody Project project) {
        return projectService.create(project);
    }

    /**
     * update / change existing project using URL params
     * @param id
     * @param projectName
     * @param projectDescription
     * @param rate
     * @param clientId
     * @return
     */
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

    /**
     * update / change existing project using json
     * @param id
     * @param project
     * @return
     */
    @PutMapping(value = "/{id}", consumes = "application/json")
    public Mono<Project> update(@PathVariable("id") String id, @RequestBody Project project) {
        if (!ControllerUtils.consistent(id, project)) {
            throw new RuntimeException("Error: path projectId and json clientId are not equal:[" + id + " vs " + project.getId() + "]");
        }
        return projectService.save(project);
    }

    /**
     * delete existing project
     * @param projectId
     * @return
     */
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String projectId) {
        return projectService.byId(projectId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("project", projectId)))
                .and(projectService.delete(projectId));
    }
}
