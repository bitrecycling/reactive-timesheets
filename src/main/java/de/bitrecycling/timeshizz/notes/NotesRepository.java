package de.bitrecycling.timeshizz.notes;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface NotesRepository extends CrudRepository<NoteEntity, UUID> {
}
