package de.bitrecycling.timeshizz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
@Document
public class Task {
    @Id
    private String id;
    private String name;
    private LocalDateTime creationTime;
    private String projectId;

    private Task(){}

    @Builder
    public Task(String name, String projectId){
        this.name = name;
        this.projectId = projectId;
        this.creationTime = LocalDateTime.now();
    }
}
