package de.bitrecycling.timeshizz.client.service;

import de.bitrecycling.timeshizz.client.model.Client;
import de.bitrecycling.timeshizz.client.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * The project service
 * <p>
 * created by robo
 */
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Flux<Client> all() {
        return clientRepository.findAllByOrderByCreationTimeDesc();
    }

    public Mono<Client> byId(String id) {
        return clientRepository.findById(id);
    }

    public Flux<Client> byName(String clientName) {
        return clientRepository.findByName(clientName);
    }


    public Mono<Client> insert(Client client) {
        if(client.getCreationTime() == null){
            client.setCreationTime(LocalDateTime.now());
        }
        return clientRepository.insert(client);
    }

    public Mono<Client> update(Client client) {
        return clientRepository.findById(client.getId()).flatMap(
                c-> {
                    client.setCreationTime(c.getCreationTime());
                    return clientRepository.save(client);
                }
        );

    }
    /**
     * Just deletes the client, no cleanup here, associated projects, their tasks and taskentries will stay
     * in persistence
     *
     * @param clientId
     * @return
     */
    public Mono<Void> delete(String clientId) {
       return clientRepository.deleteById(clientId);
    }
}
