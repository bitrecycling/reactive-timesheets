package de.bitrecyling.timeshizz.clientlib.task.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeTaskEntry {
    private LocalDateTime start;
    private LocalDateTime end;
    private String taskId;
}
