package de.bitrecycling.timeshizz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * The timeshizz application spring boot main class
 * <p>
 * created by robo
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableWebSecurity
public class TimeshizzApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeshizzApplication.class, args);
    }

}
