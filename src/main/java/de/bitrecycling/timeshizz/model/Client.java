package de.bitrecycling.timeshizz.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document
public class Client {
    @Id
    private String id;
    private String name;
    private String address;
    private Set<String> projectIds = new HashSet<>();
}
