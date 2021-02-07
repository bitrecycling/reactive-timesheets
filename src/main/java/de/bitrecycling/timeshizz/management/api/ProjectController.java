package de.bitrecycling.timeshizz.management.api;

import de.bitrecycling.timeshizz.management.model.Project;
import de.bitrecycling.timeshizz.management.service.ProjectService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST controller for the project resource. provides the usual CRUD-like operations in a restful manner.
 * <p>
 * created by robo
 */
@RestController
@RequestMapping("/projects")
@Api(value = "Project Management", description = "CRUD for project resource")
@RequiredArgsConstructor
public class ProjectController {
    
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    /**
     * get all projects
     * @return
     */
    @GetMapping
    public List<ProjectJson> all() {
        return projectService.all().stream().map(p -> projectMapper.toJson(p)).collect(Collectors.toList());
    }

    /**
     * get specific project
     * @param id the id of the project to load
     * @return
     */
    @GetMapping("/{id}")
    public Project byId(@PathVariable("id") UUID id) {
        return projectService.byId(id);
    }

    /**
     * get projects for given client
     * @param clientId the client's id to return projects for
     * @return
     */
    @GetMapping(params = "clientId")
    public List<Project> allByClientId(@RequestParam("clientId") UUID clientId) {
        return projectService.allByClientId(clientId);
    }

    /**
     * get the number of projects for given client
     * @param clientId the client's id to return project count for
     * @return
     */
    @GetMapping(value = "/_count", params = "clientId")
    public int countAllByClientId(@RequestParam("clientId") UUID clientId) {
        return projectService.countAllByClientId(clientId);
    }


    /**
     * create a project using json
     * @param project the project as json to be created
     * @return
     */
    @PostMapping(consumes = "application/json")
    public Project create(@RequestBody ProjectJson project) {
        return projectService.createProjectForClient(projectMapper.toEntity(project), project.getClientId());
    }


    /**
     * update / change existing project using json
     * @param project
     * @return
     */
    @PutMapping(consumes = "application/json")
    public Project update(@RequestBody Project project) {
        return projectService.save(project);
    }

    /**
     * delete existing project
     * @param projectId
     * @return
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID projectId) {
        projectService.delete(projectId);
    }
}
