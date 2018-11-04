package de.bitrecycling.timeshizz.clientlib.client.service;

import de.bitrecycling.timeshizz.clientlib.client.connector.ClientConnector;
import de.bitrecycling.timeshizz.clientlib.client.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

/**
 * Client service API, provides all services to the project resource. Uses the ClientConnector internally.
 * Provides some additional logic to ensure correctness and for convenience.
 * <p>
 * created by robo
 */
@Service
public class ClientService {

    @Autowired
    ClientConnector clientConnector;


    public Flux<Client> loadAllClients() {

        return clientConnector.loadAllClients();
    }

    public Mono<Client> createClient(Client client) {

        return clientConnector.createClient(client);
    }

    public Mono<Void> deleteClient(String id) {
        return clientConnector.deleteClient(id);
    }
}
