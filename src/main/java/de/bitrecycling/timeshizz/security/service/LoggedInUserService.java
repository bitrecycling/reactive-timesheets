package de.bitrecycling.timeshizz.security.service;

import de.bitrecycling.timeshizz.management.model.User;
import de.bitrecycling.timeshizz.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class LoggedInUserService {

    private final UserRepository userRepository;

    /**
     * gets logged-in user data
     * @return
     */
    public User getLoggedInUser() {
        final Object principalO = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principalO instanceof UserDetails) {
            final UserDetails userDetails = (UserDetails) principalO;
            if (userDetails.getAuthorities().contains("ROLE_USER")) {
                return userRepository.findByName(userDetails.getUsername());
            }
        }
        throw new RuntimeException("something seems wrong");
    }
}