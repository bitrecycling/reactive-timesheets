package de.bitrecycling.timeshizz.repository;

import de.bitrecycling.timeshizz.model.TaskEntry;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TaskEntryRepository extends ReactiveMongoRepository<TaskEntry, String> {
}
