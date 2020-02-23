package de.bitrecycling.timeshizz.task.service;

import de.bitrecycling.timeshizz.client.repository.ClientRepository;
import de.bitrecycling.timeshizz.common.ResourceNotFoundException;
import de.bitrecycling.timeshizz.project.repository.ProjectRespository;
import de.bitrecycling.timeshizz.task.model.TaskEntity;
import de.bitrecycling.timeshizz.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The task service
 * created by robo
 */
@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjectRespository projectRespository;

    @Autowired
    ClientRepository clientRepository;

    public List<TaskEntity> all() {
        return (List<TaskEntity>) taskRepository.findAll();
    }

    public TaskEntity byId(UUID taskId){
        return taskRepository.findById(taskId).orElseThrow(()->new ResourceNotFoundException("task", taskId.toString()));
    }

    public List<TaskEntity> allByProjectId(UUID projectId) {
        return taskRepository.findAllByProjectIdOrderByCreationTimeDesc(projectId);
    }

    public TaskEntity insert(String taskName, UUID projectId) {
        TaskEntity task = new TaskEntity();
        task.setName(taskName);
        final Optional<TaskEntity> savedTask = projectRespository.findById(projectId).map(p -> {
            task.setProject(p);
            task.setCreationTime(LocalDateTime.now());
            taskRepository.save(task);
            return task;
        });
        if(savedTask.isPresent()){
            return savedTask.get();
        }
        throw new ResourceNotFoundException(taskName,"new");
    }

    public void delete(UUID taskId) {
        taskRepository.deleteById(taskId);
    }

    public List<TaskEntity> findByCreationTimeBetween(LocalDateTime from, LocalDateTime to) {
        return taskRepository.findByCreationTimeBetween(from, to);
    }

    /**
     *
     * @param task
     * @return
     */
    public TaskEntity save(TaskEntity task) {
        return taskRepository.save(task);
    }

    /**
     * saves only possible change: taskName
     * project and client cannot be changed
     * @param id
     * @param taskName
     * @return
     */
    public TaskEntity save(UUID id, String taskName) {
        final TaskEntity task = byId(id);
        task.setName(taskName);
        return taskRepository.save(task);
    }
}
