package de.bitrecycling.timeshizz.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * The persistent project model
 *
 * created by robo
 */
@Data
@AllArgsConstructor
@Document
public class Project {

    @Id
    @Setter
    private String id;
    private String name = "no name";
    private String description = "no description";
    private String clientId;
    private LocalDateTime creationTime;
    /*
        per hour rate for this project.
     */
    private Integer rate = 0;

    private Project(){}

    @Builder
    private Project(String name, String description, Integer rate, String clientId){
        this.name = name;
        this.description = description;
        this.rate = rate;
        this.clientId = clientId;
        this.creationTime = LocalDateTime.now();
    }
}
