package de.bitrecycling.timeshizz.client.controller;

import de.bitrecycling.timeshizz.client.model.Client;
import de.bitrecycling.timeshizz.client.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * The client controller provides the endpoints to the client resource
 *
 * created by robo
 */
@RestController
@RequestMapping("/clients")
@Api(value = "Client Management", description = "CRUD for client resource")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @ApiOperation(value = "get all clients", response = Flux.class)
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
    @ApiOperation(value = "create new client", response = Flux.class)
    @PostMapping(consumes = "!application/json")
    public Mono<Client> create(@RequestParam("name") String clientName,
                               @RequestParam("address") String clientAddress){

        Client client = new Client(clientName, clientAddress);
        return clientService.insert(client);

    }

    /**
     * create a new client {"name":"Client Name", "address":"Client Adress, 234521 Some City"}
     * @param client
     * @return
     */
    @PostMapping(consumes ="!application/x-www-form-urlencoded")
    public Mono<Client> create(@RequestBody Client client){
        return clientService.insert(client);
    }

    /**
     * update a client
     * @param clientName
     * @param clientAddress
     * @return
     */
    @PutMapping(name = "/{id}", consumes ="!application/x-www-form-urlencoded")
    public Mono<Client> update(@PathVariable("id") String clientId,
                               @RequestParam("name") String clientName,
                               @RequestParam("address") String clientAddress){
        Client client = new Client(clientName,clientAddress);
        client.setId(clientId);
        return clientService.update(client);
    }

    /**
     * update a client
     * @param id
     * @param client
     * @return
     */
    @PutMapping(name = "/{id}", consumes = "!application/json")
    public Mono<Client> update(@PathVariable("id") String id,
                               @RequestBody Client client){

        if(!consistent(id, client)){
            throw new RuntimeException("Error: path id and json id are not equal:["+id+" vs "+client.getId()+"]");
        }

        return clientService.update(client);
    }

    /**
     * load a specific client
     * @param clientId
     * @return
     */
    @GetMapping("/{id}")
    public Mono<Client> getById(@PathVariable("id") String clientId){

        return clientService.byId(clientId);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String clientId){
        return clientService.delete(clientId);
    }

    private boolean consistent(String id, Client client){

        return true;
    }
}
