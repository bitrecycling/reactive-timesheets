package de.bitrecycling.timeshizz.user.model;

import lombok.Data;

@Data
public class CreateUserRequest {
    String name;
    String email;
    String password;
}
