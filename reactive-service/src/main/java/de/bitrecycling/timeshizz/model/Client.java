package de.bitrecycling.timeshizz.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * The persistent client model
 *
 * creationTime by robo
 */
@Getter
@EqualsAndHashCode
@Document
public class Client {
    @Id
    @Setter
    private String id;
    private String name;
    private String address;
    private LocalDateTime creationTime;

    private Client(){}

    @Builder
    public Client(String name, String address){
        this.name = name;
        this.address = address;
        this.creationTime = LocalDateTime.now();
    }
}
