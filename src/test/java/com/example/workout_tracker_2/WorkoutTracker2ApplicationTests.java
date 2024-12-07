package com.example.workout_tracker_2;

import com.example.workout_tracker_2.service.ExerciseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class WorkoutTracker2ApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testBean() {
        ExerciseService exerciseService = context.getBean(ExerciseService.class);
        System.out.println("ExerciseService bean: " + exerciseService);
        assert exerciseService != null : "ExerciseService bean should not be null";
    }
}