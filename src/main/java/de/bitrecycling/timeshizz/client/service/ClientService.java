package de.bitrecycling.timeshizz.client.service;

import de.bitrecycling.timeshizz.client.model.ClientEntity;
import de.bitrecycling.timeshizz.client.repository.ClientRepository;
import de.bitrecycling.timeshizz.common.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The project service
 * <p>
 * created by robo
 */
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<ClientEntity> all() {
        return clientRepository.findAllByOrderByCreationTimeDesc();
    }

    public ClientEntity byId(UUID id) {
        return clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("client", id.toString()));

    }

    public ClientEntity insert(ClientEntity client) {
        if (client.getCreationTime() == null) {
            client.setCreationTime(LocalDateTime.now());
        }
        return clientRepository.save(client);
    }

    public ClientEntity update(ClientEntity client) {
        return clientRepository.save(client);
    }

    /**
     * Just deletes the client, no cleanup here, associated projects, their tasks and taskentries will stay
     * in persistence
     *
     * @param clientId
     * @return
     */
    public void delete(UUID clientId) {
        clientRepository.deleteById(clientId);
    }
}
