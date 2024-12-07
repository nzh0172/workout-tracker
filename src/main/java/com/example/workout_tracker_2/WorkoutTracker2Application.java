package com.example.workout_tracker_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.workout_tracker_2.frontend.WorkoutApp;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.workout_tracker_2.repository")
@EntityScan(basePackages = "com.example.workout_tracker_2.entity")
@ComponentScan(basePackages = {
	    "com.example.workout_tracker_2.service",
	    "com.example.workout_tracker_2.frontend",
	    "com.example.workout_tracker_2.repository"
	})
public class WorkoutTracker2Application {
	

	public static void main(String[] args) {
	      // Start the Spring Boot backend
        SpringApplication.run(WorkoutTracker2Application.class, args);

        // Start the JavaFX frontend
        WorkoutApp.launch(WorkoutApp.class, args);	
        
	}

}

