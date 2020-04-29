package de.bitrecycling.timeshizz.activity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The activitymodel. For brevity and simplicity this is both domain and persistent model.
 *
 * created by robo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "activity")
public class ActivityEntity {
    
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @NonNull
    private String name;
    @JsonIgnore
    @NonNull
    @ManyToOne(optional = false)
    private ProjectEntity project;
    @OneToMany()
    private List<ActivityEntryEntity> activityEntries = new ArrayList<>();
    
    private LocalDateTime creationTime;

    @JsonIgnore // defensive
    private UUID userId;

}
