package de.bitrecycling.timeshizz.security.config;

import de.bitrecycling.timeshizz.management.model.User;
import de.bitrecycling.timeshizz.management.repository.UserRepository;
import de.bitrecycling.timeshizz.security.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConf extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user").permitAll()
//                .antMatchers("/**").hasRole("USER")
                .and()
                .formLogin();
//                .antMatchers(HttpMethod.POST, "/user").permitAll()
//                .antMatchers("/**").hasRole("USER")
//                .and()
//                .formLogin();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository.findByName(username);
            if (user == null) {
                throw new UsernameNotFoundException(username);
            }
            return new UserPrincipal(user);
        };
    }

}