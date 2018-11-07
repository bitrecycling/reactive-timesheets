package de.bitrecycling.timeshizz.task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
@Document
public class TaskEntry {
    @Id
    private String id;
    private LocalDateTime creationTime = null;
    private LocalDateTime startTime = null;
    private Integer durationMinutes = null;
    private String taskId;

    private TaskEntry(){}

    public TaskEntry(LocalDateTime startTime, Integer durationMinutes, String taskId){
        this.startTime=startTime;
        this.durationMinutes = durationMinutes;
        this.taskId = taskId;
        this.creationTime = LocalDateTime.now();
    }
}
