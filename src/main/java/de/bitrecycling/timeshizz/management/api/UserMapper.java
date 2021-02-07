package de.bitrecycling.timeshizz.management.api;

import de.bitrecycling.timeshizz.management.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    CreateUserResponse toJson(User entity);

    User fromJson(CreateUserRequest request);
}

