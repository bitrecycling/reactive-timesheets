package de.bitrecycling.timeshizz.client.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * The persistent client model
 *
 * created by robo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Document
public class Client {
    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String address;
    private LocalDateTime creationTime;

}
