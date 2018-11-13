package de.bitrecycling.timeshizz.task.service;

import de.bitrecycling.timeshizz.task.model.TaskEntry;
import de.bitrecycling.timeshizz.task.repository.TaskEntryRepository;
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

    public Flux<TaskEntry> all() {
        return taskEntryRepository.findAll();
    }

    public Flux<TaskEntry> allByTaskId(String taskId) {
        return taskEntryRepository.findAllByTaskIdOrderByCreationTimeDesc(taskId);
    }

    public Mono<TaskEntry> insert(TaskEntry taskEntry) {
        if(taskEntry.getCreationTime() == null){
            taskEntry.setCreationTime(LocalDateTime.now());
        }
        return taskEntryRepository.insert(taskEntry);
    }

    public Mono<Void> delete(String taskEntryId) {
        return taskEntryRepository.deleteById(taskEntryId);
    }

    public Flux<TaskEntry> findByCreationTimeBetween(LocalDateTime from, LocalDateTime to) {
        return taskEntryRepository.findAllByCreationTimeBetween(from, to);
    }

    public Flux<TaskEntry> findPagedAscending(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return taskEntryRepository.findAllByOrderByCreationTimeAsc(pageable);
    }

    public Flux<TaskEntry> findPagedDescending(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return taskEntryRepository.findAllByOrderByCreationTimeDesc(pageable);

    }

    public Mono<TaskEntry> save(TaskEntry taskEntry) {
        return taskEntryRepository.save(taskEntry);
    }
}
