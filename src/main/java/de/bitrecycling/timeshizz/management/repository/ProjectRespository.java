package de.bitrecycling.timeshizz.management.repository;

import de.bitrecycling.timeshizz.management.model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


/**
 * The project repository provides persistence the the project model
 *
 * created by robo
 */
@Repository
public interface ProjectRespository extends CrudRepository<Project, UUID> {
    List<Project> findAllByClientId(UUID clientId);

    List<Project> findAll();
}
