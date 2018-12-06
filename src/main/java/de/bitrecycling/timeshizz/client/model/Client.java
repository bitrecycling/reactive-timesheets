package de.bitrecycling.timeshizz.client.model;

import de.bitrecycling.timeshizz.common.model.Model;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * The client model. For brevity and simplicity this is both domain and persistent model.
 *
 * created by robo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Document
public class Client implements Model {
    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String address;
    private LocalDateTime creationTime;

}
