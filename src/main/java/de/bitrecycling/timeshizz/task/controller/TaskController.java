package de.bitrecycling.timeshizz.task.controller;

import de.bitrecycling.timeshizz.task.model.TaskEntity;
import de.bitrecycling.timeshizz.task.model.TaskJson;
import de.bitrecycling.timeshizz.task.model.TaskMapper;
import de.bitrecycling.timeshizz.task.service.TaskService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    private TaskMapper taskMapper;

    /**
     * get all tasks, can be used for reports or custom filtering
     *
     * @return
     */
    @GetMapping
    public List<TaskEntity> all() {
        return taskService.all();
    }

    /**
     * get all task for given project
     *
     * @param projectId
     * @return
     */
    @GetMapping(params = "projectId")
    public List<TaskEntity> allByProjectId(@RequestParam("projectId") UUID projectId) {
        return taskService.allByProjectId(projectId);
    }

    /**
     * get all task for given taskId
     *
     * @param taskId
     * @return
     */
    @GetMapping("/{id}")
    public TaskJson byId(@PathVariable("id") UUID taskId) {
        return taskMapper.toJson(taskService.byId(taskId));
    }

    /**
     * find all tasks with creation datetime between the from and to datetimes.
     *
     * @param fromString a string representation of a date like 2018-11-05T17:08:42.477Z
     * @param toString   a string representation of a date like 2018-11-05T17:08:42.477Z
     * @return
     */
    @GetMapping(params = {"from", "to"})
    public List<TaskEntity> allByCreationTime(@RequestParam("from") String fromString, @RequestParam("to") String toString) {
        LocalDateTime from = LocalDateTime.parse(fromString);
        LocalDateTime to = LocalDateTime.parse(toString);
        return taskService.findByCreationTimeBetween(from, to);
    }


    @PostMapping(consumes = "application/json")
    public TaskEntity create(@RequestBody TaskJson task) {
        return taskService.insert(task.getName(), task.getProjectId());
    }


    @PutMapping(consumes = "application/json")
    public TaskJson put(@RequestBody TaskEntity task) {
        return taskMapper.toJson(taskService.save(task));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID taskId) {
        taskService.delete(taskId);
    }
}
