package de.bitrecycling.timeshizz.user.model;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    CreateUserResponse toJson(UserEntity entity);
    UserEntity fromJson(CreateUserRequest request);
}

