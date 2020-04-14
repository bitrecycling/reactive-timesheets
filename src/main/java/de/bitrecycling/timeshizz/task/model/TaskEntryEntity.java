package de.bitrecycling.timeshizz.task.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The taskentry model. For brevity and simplicity this is both domain and persistent model.
 *
 * by robo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class TaskEntryEntity {
    
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @NonNull
    private LocalDateTime startTime;
    @NonNull
    private Integer durationMinutes;
    @ManyToOne(optional = false)
//    @JoinColumn(referencedColumnName = "id", insertable=false, updatable=false)
    private TaskEntity task;
    private LocalDateTime creationTime;
    
}
