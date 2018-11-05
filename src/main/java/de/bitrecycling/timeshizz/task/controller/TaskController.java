package de.bitrecycling.timeshizz.task.controller;

import de.bitrecycling.timeshizz.task.model.Task;
import de.bitrecycling.timeshizz.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

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

    /**
     * get all tasks, can be used for reports or custom filtering
     * @return
     */
    @GetMapping
    public Flux<Task> all(){
        return taskService.all();
    }

    /**
     * get all task for given project
     * @param projectId
     * @return
     */
    @GetMapping(params = "projectId")
    public Flux<Task> allByProjectId(@RequestParam("projectId") String projectId){
        return taskService.allByProjectId(projectId);
    }

    /**
     * find all tasks with creation datetime between the from and to datetimes.
     * @param fromString a string representation of a date like 2018-11-05T17:08:42.477Z
     * @param toString a string representation of a date like 2018-11-05T17:08:42.477Z
     * @return
     */
    @GetMapping(params = {"from","to"})
    public Flux<Task> allByCreationTime(@RequestParam("from") String fromString, @RequestParam("to") String toString){
        LocalDateTime from = LocalDateTime.parse(fromString);
        LocalDateTime to = LocalDateTime.parse(toString);
        return taskService.findByCreationTimeBetween(from, to);
    }

    /**
     * create and persist a new task for the given project, with the given name
     * @param taskName
     * @param projectId
     * @return
     */
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
