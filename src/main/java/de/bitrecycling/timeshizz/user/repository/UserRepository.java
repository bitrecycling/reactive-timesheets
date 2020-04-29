package de.bitrecycling.timeshizz.user.repository;

import de.bitrecycling.timeshizz.user.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByName(String username);
    UserEntity findByEmail(String email);
}