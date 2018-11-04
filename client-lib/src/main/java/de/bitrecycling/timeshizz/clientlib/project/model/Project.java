package de.bitrecycling.timeshizz.clientlib.project.model;

import lombok.Data;

/**
 * Project model, provides all attributes necessary for the project resource.
 * created by robo
 */
@Data
public class Project {
    private String id;
    private String name;
    private String description;
    private String clientId;
}
