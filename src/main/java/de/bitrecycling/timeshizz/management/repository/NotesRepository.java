package de.bitrecycling.timeshizz.management.repository;

import de.bitrecycling.timeshizz.management.model.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotesRepository extends CrudRepository<Note, UUID> {
}
