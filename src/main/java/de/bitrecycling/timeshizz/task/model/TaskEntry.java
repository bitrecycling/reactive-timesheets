package de.bitrecycling.timeshizz.task.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * The persistent taskentry model
 *
 * by robo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Document
public class TaskEntry {
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
