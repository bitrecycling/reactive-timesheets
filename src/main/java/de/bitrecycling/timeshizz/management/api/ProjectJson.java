package de.bitrecycling.timeshizz.management.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectJson {
    
    private UUID id;
    private String name;
    private String description;
    private Double rate;
    private UUID clientId;
}
