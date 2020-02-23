package de.bitrecycling.timeshizz.task.model;

import de.bitrecycling.timeshizz.project.model.ProjectEntity;
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
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The task model. For brevity and simplicity this is both domain and persistent model.
 *
 * created by robo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class TaskEntity {
    
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @NonNull
    private String name;
    @NonNull
    @OneToOne
    private ProjectEntity project;
    
    private LocalDateTime creationTime;

}
