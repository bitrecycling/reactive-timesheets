package de.bitrecyling.timeshizz.clientlib.task.model;

import lombok.Data;

@Data
public class DurationTaskEntry {
    private String id;
    private Integer durationMinutes;
    private String taskId;
}
