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
    private LocalDateTime start = null;
    private LocalDateTime end = null;
    private Duration duration = null;
    private String taskId;

    private TaskEntry(){}

    @Builder
    public TaskEntry(LocalDateTime start, LocalDateTime end, String taskId){
        this.start = start;
        this.end = end;
        this.taskId = taskId;
        this.creationTime = LocalDateTime.now();

    }

    @Builder
    public TaskEntry(Duration duration, String taskId){
        this.duration = duration;
        this.taskId = taskId;
        this.creationTime = LocalDateTime.now();
    }


    public Duration getDuration() {
        if(start == null || end == null){
            return duration;
        }else {
            return Duration.between(start,end);
        }
    }
}
