package de.bitrecycling.timeshizz.project.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * The persistent project model
 *
 * created by robo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Document
public class Project {

    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private Double rate;
    @NonNull
    private String clientId;
    private LocalDateTime creationTime;

}
