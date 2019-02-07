package de.bitrecycling.timeshizz.task.service;

import de.bitrecycling.timeshizz.client.repository.ClientRepository;
import de.bitrecycling.timeshizz.common.ResourceNotFoundException;
import de.bitrecycling.timeshizz.project.repository.ProjectRespository;
import de.bitrecycling.timeshizz.task.model.TaskEntry;
import de.bitrecycling.timeshizz.task.repository.TaskEntryRepository;
import de.bitrecycling.timeshizz.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * The task service
 * created by robo
 */
@Service
public class TaskEntryService {

    @Autowired
    private TaskEntryRepository taskEntryRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectRespository projectRespository;
    @Autowired
    private ClientRepository clientRepository;

    public Flux<TaskEntry> all() {
        return taskEntryRepository.findAll();
    }

    public Flux<TaskEntry> getAllByTaskId(String taskId) {
        return taskEntryRepository.findAllByTaskIdOrderByCreationTimeDesc(taskId);
    }

    public Flux<TaskEntry> getMostRecentByStartTime(Integer count) {
        PageRequest of = PageRequest.of(0, count);
        return taskEntryRepository.findAllByOrderByStartTimeDesc(of);
    }

    public Flux<TaskEntry> getByCreationTimeBetween(LocalDateTime from, LocalDateTime to) {
        return taskEntryRepository.findAllByCreationTimeBetween(from, to);
    }

    public Flux<TaskEntry> getByStartTimeBetween(LocalDateTime from, LocalDateTime to) {
        return taskEntryRepository.findAllByStartTimeBetween(from, to);
    }

    public Flux<TaskEntry> getByClientAndByStartTimeBetween(String clientId, LocalDateTime from, LocalDateTime to) {
        return taskEntryRepository.findAllByClientIdAndStartTimeBetweenOrderByStartTimeDesc(clientId, from, to);
    }

    public Flux<TaskEntry> getByStartTimeBetween(LocalDateTime from, LocalDateTime to, String taskId) {
        return taskEntryRepository.findAllByTaskIdAndStartTimeBetween(taskId, from, to);
    }

    public Flux<TaskEntry> getPagedAscending(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskEntryRepository.findAllByOrderByCreationTimeAsc(pageable);
    }

    public Flux<TaskEntry> getPagedDescending(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskEntryRepository.findAllByOrderByCreationTimeDesc(pageable);

    }

    public Mono<TaskEntry> insert(LocalDateTime startTime, Integer durationMinutes, String taskId) {

        TaskEntry taskEntry = new TaskEntry();
        taskEntry.setStartTime(startTime);
        taskEntry.setDurationMinutes(durationMinutes);
        taskEntry.setTaskId(taskId);

        Mono<TaskEntry> taskEntryMono = taskRepository.findById(taskId).flatMap(task ->
                projectRespository.findById(task.getProjectId())
                        .flatMap(project -> clientRepository.findById(project.getClientId())
                                .flatMap(client -> {
                                    taskEntry.setProjectId(project.getId());
                                    taskEntry.setClientId(client.getId());
                                    taskEntry.setCreationTime(LocalDateTime.now());
                                    return taskEntryRepository.insert(taskEntry);
                                })));
        return taskEntryMono;
    }

    public Mono<TaskEntry> byId(String id) {
        return taskEntryRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("taskentry", id)));
    }

    public Mono<TaskEntry> save(String id, LocalDateTime parsedTime, int durationMinutes, String taskId) {
        return byId(id).flatMap(taskEntry -> taskEntryRepository.save(new TaskEntry(id, parsedTime, durationMinutes, taskId, taskEntry.getProjectId(), taskEntry.getClientId(), taskEntry.getCreationTime())));
    }

    public Mono<TaskEntry> save(TaskEntry taskEntry) {
        return byId(taskEntry.getId()).then(taskEntryRepository.save(taskEntry));
    }

    public Mono<Void> delete(String taskEntryId) {
        return byId(taskEntryId).then(taskEntryRepository.deleteById(taskEntryId));
    }

}
