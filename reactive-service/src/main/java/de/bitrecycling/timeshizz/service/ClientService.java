package de.bitrecycling.timeshizz.service;

import de.bitrecycling.timeshizz.model.Client;
import de.bitrecycling.timeshizz.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Flux<Client> all(){
        return clientRepository.findAll();
    }
    public Mono<Client> byId(String id){
        return clientRepository.findById(id);
    }

    public Mono<Client> byName(String name){
        return clientRepository.findDistinctFirstByName(name);
    }


    public Mono<Client> insert(Client client){
        return clientRepository.insert(client);
    }

}
