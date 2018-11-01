package de.bitrecycling.timeshizz.service;

import de.bitrecycling.timeshizz.model.TaskEntry;
import de.bitrecycling.timeshizz.repository.TaskEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The task service
 * creationTime by robo
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
}
