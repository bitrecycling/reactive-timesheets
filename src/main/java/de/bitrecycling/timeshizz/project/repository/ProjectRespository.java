package de.bitrecycling.timeshizz.project.repository;

import de.bitrecycling.timeshizz.project.model.ProjectEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;


/**
 * The project repository provides persistence the the project model
 *
 * created by robo
 */
public interface ProjectRespository extends CrudRepository<ProjectEntity, UUID> {
    List<ProjectEntity> findAllByClientId(UUID clientId);
    List<ProjectEntity> findAll();
}
