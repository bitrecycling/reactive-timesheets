package de.bitrecycling.timeshizz;

import com.mongodb.MongoClientOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The timeshizz application spring boot main class
 *
 * created by robo
 */
@SpringBootApplication
public class TimeshizzApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeshizzApplication.class, args);
	}

}
