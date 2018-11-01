package de.bitrecycling.timeshizz.controller;

import de.bitrecycling.timeshizz.model.Client;
import de.bitrecycling.timeshizz.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The client controller provides the endpoints to the client resource
 *
 * creationTime by robo
 */
@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public Flux<Client> all(){
        return clientService.all();
    }

    @PostMapping
    public Mono<Client> create(@RequestBody Client client){
        return clientService.insert(client);

    }
}
