package de.bitrecycling.timeshizz.task.model;

import de.bitrecycling.timeshizz.common.model.Model;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * The persistent task model
 *
 * created by robo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Document
public class Task implements Model {
    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String projectId;
    private LocalDateTime creationTime;

}
