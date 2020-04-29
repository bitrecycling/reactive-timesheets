package de.bitrecycling.timeshizz.project.model;

import de.bitrecycling.timeshizz.activity.model.ActivityEntity;
import de.bitrecycling.timeshizz.client.model.ClientEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

/**
 * The project model. For brevity and simplicity this is both domain and persistent model.
 *
 * created by robo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project")
public class ProjectEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private Double rate;
    @NonNull
    @ManyToOne(optional = false)
    private ClientEntity client;
    @OneToMany
    private List<ActivityEntity> activities;
}
