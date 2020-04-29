package de.bitrecycling.timeshizz.user.model;

import lombok.Data;

@Data
public class CreateUserResponse {
    Long id;
    String name;
    String email;
}
