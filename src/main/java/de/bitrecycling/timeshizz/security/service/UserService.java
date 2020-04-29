package de.bitrecycling.timeshizz.security.service;

import de.bitrecycling.timeshizz.security.model.UserPrincipal;
import de.bitrecycling.timeshizz.user.model.UserEntity;
import de.bitrecycling.timeshizz.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("UserDetailsService")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }

    public UserEntity createUser(String username, String password, String email) {
        final UserEntity userEntity = new UserEntity();
        userEntity.setName(username);
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setEmail(email);
        return userRepository.save(userEntity);
    }
}