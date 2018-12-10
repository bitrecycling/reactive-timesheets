package de.bitrecycling.timeshizz.client.repository;

import de.bitrecycling.timeshizz.client.model.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
 * The client repository provides persistence the the client model
 *
 * created by robo
 */
public interface ClientRepository extends ReactiveMongoRepository<Client, String> {
    Flux<Client> findAllByOrderByCreationTimeDesc();
}
