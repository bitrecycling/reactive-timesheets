package de.bitrecycling.timeshizz.management.api;

import lombok.Data;

@Data
public class CreateUserRequest {
    String clientId;
    String name;
    String email;
    String password;
}
