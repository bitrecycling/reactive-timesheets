package de.bitrecycling.timeshizz.repository;

import de.bitrecycling.timeshizz.model.Client;
import de.bitrecycling.timeshizz.model.Project;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectRespository extends ReactiveMongoRepository<Project, String> {
    default Flux<Project> findAllByClient(Mono<Client> client){
        return client.flatMapMany(c-> this.findAllById(c.getProjectIds()));
    };
}
