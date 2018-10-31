package de.bitrecycling.timeshizz.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document
public class Project {

    @Id
    private String id;
    private String name = "no name";
    private String description = "no description";
    /*
        per hour rate for this project.
     */
    private Integer rate = 0;
    private Set<String> taskIds = new HashSet<>();

    public Project(){}

    private Project(String name, String description, Integer rate){
        this.name = name;
        this.description = description;
        this.rate = rate;
    }
}
