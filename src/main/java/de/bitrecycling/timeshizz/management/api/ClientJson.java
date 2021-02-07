package de.bitrecycling.timeshizz.management.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientJson {
    private UUID id;
    private String name;
    private String address;
    private LocalDateTime creationTime;
    private List<ProjectJson> projects;

}
