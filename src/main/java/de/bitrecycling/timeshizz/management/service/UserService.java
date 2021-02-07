package de.bitrecycling.timeshizz.management.service;

import de.bitrecycling.timeshizz.management.model.User;
import de.bitrecycling.timeshizz.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User createUser(String username, String password, String email) {
        final User userEntity = new User();
        userEntity.setName(username);
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setEmail(email);
        return userRepository.save(userEntity);
    }
}
