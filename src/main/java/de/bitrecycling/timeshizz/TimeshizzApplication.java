package de.bitrecycling.timeshizz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * The timeshizz application spring boot main class
 * <p>
 * created by robo
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class TimeshizzApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeshizzApplication.class, args);
    }

}


@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
//                .antMatchers("/index").permitAll()
                .antMatchers("/*").hasRole("USER")
                .and()
                .formLogin();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        UserDetails user = User.withDefaultPasswordEncoder().username("timeshizz").password("timeshizz").roles("USER").build();
        auth
                .inMemoryAuthentication()
                .withUser(user);
    }
}