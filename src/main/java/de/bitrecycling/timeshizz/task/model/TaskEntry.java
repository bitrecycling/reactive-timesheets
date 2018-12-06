package de.bitrecycling.timeshizz.task.model;

import de.bitrecycling.timeshizz.common.model.Model;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * The taskentry model. For brevity and simplicity this is both domain and persistent model.
 *
 * by robo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Document
public class TaskEntry implements Model {
    @Id
    private String id;
    @NonNull
    private LocalDateTime startTime;
    @NonNull
    private Integer durationMinutes;
    @NonNull
    private String taskId;
    private LocalDateTime creationTime;

}
