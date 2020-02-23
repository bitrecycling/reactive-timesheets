package de.bitrecycling.timeshizz.task.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class TaskJson {
    @Id
    private UUID id;
    @NonNull
    private String name;
    @NonNull
    private UUID projectId;
    
    private LocalDateTime creationTime;

}
