package de.bitrecycling.timeshizz.repository;

import de.bitrecycling.timeshizz.model.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TaskRepository extends ReactiveMongoRepository<Task, String> {
}
