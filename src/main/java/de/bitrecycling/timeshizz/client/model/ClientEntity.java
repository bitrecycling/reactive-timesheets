package de.bitrecycling.timeshizz.client.model;

import de.bitrecycling.timeshizz.project.model.ProjectEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The client model. For brevity and simplicity this is both domain and persistent model.
 *
 * created by robo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client")
public class ClientEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @NonNull
    private String name;
    @NonNull
    private String address;
    private LocalDateTime creationTime;
    @OneToMany
    private List<ProjectEntity> projects;

}
