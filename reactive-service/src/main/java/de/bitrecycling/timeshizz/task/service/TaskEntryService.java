package de.bitrecycling.timeshizz.task.service;

import de.bitrecycling.timeshizz.task.model.TaskEntry;
import de.bitrecycling.timeshizz.task.repository.TaskEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    public Mono<TaskEntry> insert(TaskEntry taskEntry) {
        return taskEntryRepository.insert(taskEntry);
    }

    public Mono<Void> delete(String taskEntryId) {
        return taskEntryRepository.deleteById(taskEntryId);
    }
}
