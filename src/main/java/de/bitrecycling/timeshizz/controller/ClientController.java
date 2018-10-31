package de.bitrecycling.timeshizz.controller;

import de.bitrecycling.timeshizz.model.Client;
import de.bitrecycling.timeshizz.service.ClientService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public Flux<Client> all(){
        return clientService.all();
    }

    @PostMapping
    public Mono<Client> create(@RequestBody Client client){
        return clientService.insert(client);

    }
}
