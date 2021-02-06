package de.bitrecycling.timeshizz.notes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * a note for a client, project, activity. Intended to have space for more detailed information. 
 *
 * How and why:
 *  in other tools I miss the feature to just note down specifics, useful contacts, a "diary" or journal
 *  for the project. might be useful if there's a question on the timesheet report to have some
 *  additional information at hand that had otherwise just been forgotten.
 *
 * Intentionally notes are user "private" and shall never get printed on a report.
 *
 * a note has a mime type and a blob in order to store any kind of note, e.g.:
 * - text
 * - images
 * - audio records
 *
 * Warning: currently no encryption is planned, so if you want to store sensitive data, handle as if you
 * you were using a filesystem.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notes")
public class NoteEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @NonNull
    private String mimeType;
    private String metaInfo;
    @NonNull
    private LocalDateTime time;
    @NonNull
    private byte[] content;
}
