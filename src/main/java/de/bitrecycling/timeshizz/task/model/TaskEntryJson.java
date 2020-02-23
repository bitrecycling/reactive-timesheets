package de.bitrecycling.timeshizz.task.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntryJson {
    @Id
    private UUID id;
    @NonNull
    private LocalDateTime startTime;
    @NonNull
    private Integer durationMinutes;
    @NonNull
    private UUID taskId;
    private LocalDateTime creationTime;

}
