package de.bitrecycling.timeshizz.management.repository;

import de.bitrecycling.timeshizz.management.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String username);

    User findByEmail(String email);
}