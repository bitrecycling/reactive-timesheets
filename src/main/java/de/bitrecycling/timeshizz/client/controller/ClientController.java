package de.bitrecycling.timeshizz.client.controller;

import de.bitrecycling.timeshizz.client.model.ClientEntity;
import de.bitrecycling.timeshizz.client.model.ClientJson;
import de.bitrecycling.timeshizz.client.model.ClientMapper;
import de.bitrecycling.timeshizz.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST controller for the client resource. provides the usual CRUD-like operations in a restful manner.
 *
 * created by robo
 */
@RestController
@RequestMapping("/clients")

public class ClientController {
    
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientController(ClientService clientService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @GetMapping
    public List<ClientJson> all(){
        return clientService.all().stream().map(c->clientMapper.toJson(c)).collect(Collectors.toList());
    }

   

    /**
     * create a new client using json, e.g. {"name":"Client Name", "address":"Client Adress, 234521 Some City"}
     * @param client json
     * @return the newly created client as json including the resource id.
     */
    @PostMapping(consumes ="!application/x-www-form-urlencoded")
    public ClientEntity create(@RequestBody ClientJson client){
        return clientService.insert(clientMapper.toEntity(client));
    }
    

    /**
     * update a client using json
     * @param client the json reflecting the changed and unchanged client data
     * @return
     */
    @PutMapping(consumes = "application/json")
    public ClientEntity update( @RequestBody ClientJson client){

        return clientService.update(clientMapper.toEntity(client));
    }

    /**
     * load a specific client
     * @param clientId the id of the client to load
     * @return
     */
    @GetMapping("/{id}")
    public ClientJson getById(@PathVariable("id") UUID clientId){
        return clientMapper.toJson(clientService.byId(clientId));
    }

    /**
     * delete client
     * @param clientId the id as path variable of the client to be deleted
     * @return
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID clientId){
        clientService.delete(clientId);
    }

}
