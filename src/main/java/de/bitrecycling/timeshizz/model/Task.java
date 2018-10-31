package de.bitrecycling.timeshizz.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Document
public class Task {
    @Id
    private String id;
    private String name;
    private LocalDateTime creationTime;
    private Set<String> taskEntryIds = new HashSet<>();
}
