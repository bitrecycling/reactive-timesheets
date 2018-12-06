package de.bitrecycling.timeshizz.task.controller;

import de.bitrecycling.timeshizz.common.controller.ControllerUtils;
import de.bitrecycling.timeshizz.task.model.Task;
import de.bitrecycling.timeshizz.task.service.TaskService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * REST controller for the task resource. provides the usual CRUD-like operations in a restful manner.
 * <p>
 * created by robo
 */
@RestController
@RequestMapping("/tasks")
@Api(value = "Task Management", description = "CRUD for task resource")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * get all tasks, can be used for reports or custom filtering
     *
     * @return
     */
    @GetMapping
    public Flux<Task> all() {
        return taskService.all();
    }

    /**
     * get all task for given project
     *
     * @param projectId
     * @return
     */
    @GetMapping(params = "projectId")
    public Flux<Task> allByProjectId(@RequestParam("projectId") String projectId) {
        return taskService.allByProjectId(projectId);
    }

    /**
     * get all task for given taskId
     *
     * @param taskId
     * @return
     */
    @GetMapping("/{id}")
    public Mono<Task> byId(@PathVariable("id") String taskId) {
        return taskService.byId(taskId);
    }

    /**
     * find all tasks with creation datetime between the from and to datetimes.
     *
     * @param fromString a string representation of a date like 2018-11-05T17:08:42.477Z
     * @param toString   a string representation of a date like 2018-11-05T17:08:42.477Z
     * @return
     */
    @GetMapping(params = {"from", "to"})
    public Flux<Task> allByCreationTime(@RequestParam("from") String fromString, @RequestParam("to") String toString) {
        LocalDateTime from = LocalDateTime.parse(fromString);
        LocalDateTime to = LocalDateTime.parse(toString);
        return taskService.findByCreationTimeBetween(from, to);
    }

    /**
     * create and persist a new task for the given project, with the given name
     *
     * @param taskName
     * @param projectId
     * @return
     */
    @PostMapping(consumes = "application/x-www-form-urlencoded")
    public Mono<Task> create(@RequestParam("name") String taskName, @RequestParam("projectId") String projectId) {
        Task task = new Task(taskName, projectId);
        return taskService.insert(task);
    }

    @PostMapping(consumes = "application/json")
    public Mono<Task> create(@RequestBody Task task) {
        return taskService.insert(task);
    }

    @PutMapping(value = "/{id}", consumes = "application/x-www-form-urlencoded")
    public Mono<Task> put(@RequestParam("id") String id, @RequestParam("name") String taskName, @RequestParam("projectId") String projectId) {
        Task task = new Task(taskName, projectId);
        task.setId(id);
        return taskService.save(task);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public Mono<Task> put(@RequestParam("id") String id, @RequestBody Task task) {
        if (!ControllerUtils.consistent(id, task)) {
            throw new RuntimeException("Error: path id and json id are not equal:[" + id + " vs " + task.getId() + "]");
        }
        return taskService.save(task);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String taskId) {
        return taskService.delete(taskId);
    }
}
