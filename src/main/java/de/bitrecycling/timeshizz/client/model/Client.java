package de.bitrecycling.timeshizz.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
@Document
public class Client {
    @Id
    private String id;
    private String name;
    private String address;
    private LocalDateTime creationTime;

    private Client(){}


    public Client(String name, String address){
        this.id = id;
        this.name = name;
        this.address = address;
        this.creationTime = LocalDateTime.now();
    }
}
