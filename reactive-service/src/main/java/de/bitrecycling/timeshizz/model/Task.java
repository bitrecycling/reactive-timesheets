package de.bitrecycling.timeshizz.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * The persistent task model
 *
 * creationTime by robo
 */
@Getter
@EqualsAndHashCode
@Document
public class Task {
    @Id
    @Setter
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
