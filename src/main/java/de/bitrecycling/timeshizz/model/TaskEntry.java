package de.bitrecycling.timeshizz.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Document
public class TaskEntry {
    @Id
    private String id;
    private LocalDateTime start = null;
    private LocalDateTime end = null;
    private Duration duration = null;

    private TaskEntry(){}

    public TaskEntry(LocalDateTime start, LocalDateTime end){
        this.start = start;
        this.end = end;
    }

    public TaskEntry(Duration duration){
        this.duration = duration;
    }


    public Duration getDuration() {
        if(start == null || end == null){
            return duration;
        }else {
            return Duration.between(start,end);
        }
    }
}
