package de.bitrecycling.timeshizz.repository;

import de.bitrecycling.timeshizz.model.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
 * The client repository provides persistence the the client model
 *
 * created by robo
 */
public interface ClientRepository extends ReactiveMongoRepository<Client, String> {
    Flux<Client> findByName(String name);
}
