package de.bitrecycling.timeshizz.activity.model;

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
public class ActivityEntryJson {
    @Id
    private UUID id;
    @NonNull
    private String startTime;
    @NonNull
    private Integer durationMinutes;
    @NonNull
    private UUID activityId;
    private LocalDateTime creationTime;

}
