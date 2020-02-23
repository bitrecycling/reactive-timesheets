package de.bitrecycling.timeshizz.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientJson {
    private UUID id;
    private String name;
    private String address;
    private LocalDateTime creationTime;

}
