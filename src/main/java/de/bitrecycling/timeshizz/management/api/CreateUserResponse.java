package de.bitrecycling.timeshizz.management.api;

import lombok.Data;

@Data
public class CreateUserResponse {
    Long id;
    String name;
    String email;
}
