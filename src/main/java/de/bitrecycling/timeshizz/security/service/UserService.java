package de.bitrecycling.timeshizz.security.service;

import de.bitrecycling.timeshizz.security.model.UserPrincipal;
import de.bitrecycling.timeshizz.user.model.CreateUserResponse;
import de.bitrecycling.timeshizz.user.model.UserEntity;
import de.bitrecycling.timeshizz.user.model.UserMapper;
import de.bitrecycling.timeshizz.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserMapper userMapper;


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

    /**
     * gets logged-in user data
     * @return
     */
    public CreateUserResponse getLoggedInUser() {
        final Object principalO = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principalO instanceof UserDetails) {
            final UserDetails userDetails = (UserDetails) principalO;
            if (userDetails.getAuthorities().contains("ROLE_USER")) {
                return userMapper.toJson(userRepository.findByName(userDetails.getUsername()));
            }
        }
        throw new RuntimeException("something seems wrong");
    }
}