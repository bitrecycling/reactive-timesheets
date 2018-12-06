package de.bitrecycling.timeshizz.client.controller;

import de.bitrecycling.timeshizz.client.model.Client;
import de.bitrecycling.timeshizz.client.service.ClientService;
import de.bitrecycling.timeshizz.common.controller.ControllerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for the client resource. provides the usual CRUD-like operations in a restful manner.
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
     * create a new client using url parameters
     * @param clientName the name of the client
     * @param clientAddress the address of the client
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
     * create a new client using json, e.g. {"name":"Client Name", "address":"Client Adress, 234521 Some City"}
     * @param client json
     * @return the newly created client as json including the resource id.
     */
    @PostMapping(consumes ="!application/x-www-form-urlencoded")
    public Mono<Client> create(@RequestBody Client client){
        return clientService.insert(client);
    }

    /**
     * update a client using url parameters
     * @param clientName the new name of the client
     * @param clientAddress the new address of the client
     * @return
     */
    @PutMapping(value = "/{id}", consumes ="application/x-www-form-urlencoded")
    public Mono<Client> update(@PathVariable("id") String clientId,
                               @RequestParam("name") String clientName,
                               @RequestParam("address") String clientAddress){
        Client client = new Client(clientName,clientAddress);
        client.setId(clientId);
        return clientService.update(client);
    }

    /**
     * update a client using json
     * @param id the id of the client to be updated
     * @param client the json reflecting the changed and unchanged client data
     * @return
     */
    @PutMapping(value = "/{id}", consumes = "application/json")
    public Mono<Client> update(@PathVariable("id") String id,
                               @RequestBody Client client){

        if(!ControllerUtils.consistent(id, client)){
            throw new RuntimeException("Error: path id and json id are not equal:["+id+" vs "+client.getId()+"]");
        }

        return clientService.update(client);
    }

    /**
     * load a specific client
     * @param clientId the id of the client to load
     * @return
     */
    @GetMapping("/{id}")
    public Mono<Client> getById(@PathVariable("id") String clientId){
        return clientService.byId(clientId);
    }

    /**
     * delete client
     * @param clientId the id as path variable of the client to be deleted
     * @return
     */
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String clientId){
        return clientService.delete(clientId);
    }

}
