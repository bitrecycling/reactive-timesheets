package de.bitrecycling.timeshizz.management.repository;

import de.bitrecycling.timeshizz.management.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * The client repository provides persistence the the client model
 *
 * created by robo
 */
@Repository
public interface ClientRepository extends CrudRepository<Client, UUID> {
    List<Client> findAllByOrderByCreationTimeDesc();

    List<Client> findAll();
}
