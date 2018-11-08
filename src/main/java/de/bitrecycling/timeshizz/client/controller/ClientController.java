package de.bitrecycling.timeshizz.client.controller;

import de.bitrecycling.timeshizz.client.model.Client;
import de.bitrecycling.timeshizz.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The client controller provides the endpoints to the client resource
 *
 * created by robo
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

    /**
     * create a new client with name and address
     * @param clientName
     * @param clientAddress
     * @return
     */
    @PostMapping
    public Mono<Client> create(@RequestParam("name") String clientName,
                               @RequestParam("address") String clientAddress){

        Client client = new Client(clientName, clientAddress);
        return clientService.insert(client);

    }

    /**
     * update a client
     * @param clientName
     * @param clientAddress
     * @return
     */
    @PutMapping("/{id}")
    public Mono<Client> update(@PathVariable("id") String clientId,
                               @RequestParam("name") String clientName,
                               @RequestParam("address") String clientAddress){

        return clientService.update(clientId, clientName, clientAddress);
    }

    /**
     * load a specific client
     * @param clientId
     * @return
     */
    @GetMapping("/{id}")
    public Mono<Client> update(@PathVariable("id") String clientId){

        return clientService.byId(clientId);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String clientId){
        return clientService.delete(clientId);
    }
}
