package de.bitrecycling.timeshizz.repository;

import de.bitrecycling.timeshizz.model.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ClientRepository extends ReactiveMongoRepository<Client, String> {
    Mono<Client> findDistinctFirstByName(String name);
}
