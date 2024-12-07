package com.example.workout_tracker_2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.workout_tracker_2.service.ExerciseService;
import com.example.workout_tracker_2.repository.ExerciseRepository;

@Configuration
public class AppConfig {

    @Bean
    public ExerciseService exerciseService(ExerciseRepository exerciseRepository) {
        return new ExerciseService(exerciseRepository);
    }
}