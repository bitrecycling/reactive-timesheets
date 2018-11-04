package de.bitrecyling.timeshizz.clientlib.task.service;

import de.bitrecyling.timeshizz.clientlib.task.connector.TaskConnector;
import de.bitrecyling.timeshizz.clientlib.task.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Task Service API, provides all services to the project resource. Uses the TaskConnector internally.
 * Provides some additional logic to ensure correctness and for convenience.
 * <p>
 * created by robo
 */
@Service
public class TaskService {

    @Autowired
    TaskConnector taskConnector;

    /**
     * load all tasks
     * @return
     */
    public Flux<Task> loadAllTasks() {

        return taskConnector.loadAllTasks();
    }

    /**
     * load all tasks for specific project
     * @return
     */
    public Flux<Task> loadAllTasksForProject(String projectId) {

        return taskConnector.loadAllTasksForProject(projectId);
    }

    /**
     * create task with name and projectId
     *
     * @param task
     * @return
     */
    public Mono<Task> createTask(Task task) {
        if (task.getProjectId().isEmpty()) {
            throw new RuntimeException("task has no projectId, task can only be created for existing project");
        }

        return taskConnector.createTask(task);
    }

    public Mono<Void> deleteTask(String id) {
        return taskConnector.deleteTask(id);
    }
}
