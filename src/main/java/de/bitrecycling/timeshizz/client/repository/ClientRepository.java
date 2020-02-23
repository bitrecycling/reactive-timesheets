package de.bitrecycling.timeshizz.client.repository;

import de.bitrecycling.timeshizz.client.model.ClientEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

/**
 * The client repository provides persistence the the client model
 *
 * created by robo
 */
public interface ClientRepository extends CrudRepository<ClientEntity, UUID> {
    List<ClientEntity> findAllByOrderByCreationTimeDesc();
}
