package de.bitrecycling.timeshizz.management.service;

import de.bitrecycling.timeshizz.common.ResourceNotFoundException;
import de.bitrecycling.timeshizz.management.model.Client;
import de.bitrecycling.timeshizz.management.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 *
 * The project service
 * <p>
 * created by robo
 */
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> all() {
        return clientRepository.findAllByOrderByCreationTimeDesc();
    }

    public Client byId(UUID id) {
        return clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("client", id));

    }

    public Client insert(Client client) {
        if (client.getCreationTime() == null) {
            client.setCreationTime(LocalDateTime.now());
        }
        return clientRepository.save(client);
    }

    public Client update(Client client) {
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
